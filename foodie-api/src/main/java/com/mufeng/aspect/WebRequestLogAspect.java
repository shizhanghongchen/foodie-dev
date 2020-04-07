package com.mufeng.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.Modifier;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @description: 日志切面
 * AOP通知 :
 * 1. 前置通知 : 在方法调用之前通知;
 * 2. 后置通知 : 在方法正常调用之后通知;
 * 3. 环绕通知 : 在方法调用之前和之后都分别可以执行的通知;
 * 4. 异常通知 : 如果在方法调用过程中发生异常则执行通知;
 * 5. 最终通知 : 在方法调用之后执行;
 * @Author: my.yang
 * @Date: 2020/4/5 12:15 AM
 */
@Aspect
@Component
public class WebRequestLogAspect {

    private Logger log = LoggerFactory.getLogger(WebRequestLogAspect.class);

    /**
     * Controller层接口日志记录 : 切面表达式
     * Before : 在方法执行前进行切面
     * execution : 代表所要执行的表达式主体;
     * 第一处(public *) : 匹配所有目标类的public方法,不写则匹配所有访问权限
     * 第二处(*) : * 代表方法返回类型表示所有类型;
     * 第三处(path) : 包名代表aop监控的类所在的包;
     * 第四处(..) : 代表该包以及其子包下的所有类方法;
     * 第五处(*) : 代表类名,*代表所有类;
     * 第六处( *(..) ) : *代表类中的方法名,(..)表示方法中的任何参数;
     *
     * @param point 切面
     */
    @Before("execution(public * com.mufeng.controller..*.*(..))")
    public void before(JoinPoint point) {
        this.logMethodParams(point);
    }

    /**
     * 获取方法参数
     *
     * @param joinPoint
     */
    public void logMethodParams(JoinPoint joinPoint) {
        // 获取正在执行的Controller
        Class klass = joinPoint.getTarget().getClass();
        String className = klass.getName();
        // 获取正在执行的方法
        String methodName = joinPoint.getSignature().getName();
        log = LoggerFactory.getLogger(klass);
        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        log.info("********************** Web Request Log Begin **********************");
        log.info("class name: " + className);
        log.info("method name: " + methodName);
        // 获取请求参数
        Object[] methodArgs = joinPoint.getArgs();
        try {
            /**
             * 获取方法参数名称
             */
            String[] paramNames = getFieldsName(className, methodName);

            /**
             * 打印方法的参数名和参数值
             */
            logParam(paramNames, methodArgs);
            log.info("********************** Web Request Log End **********************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字段名称
     *
     * @param className
     * @param methodName
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String className, String methodName) throws Exception {
        Class<?> klass = Class.forName(className);
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(klass);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(className);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }

    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    private boolean isPrimitive(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            log.info("Method has no params");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name + " = ");
            if (null == value) {
                buffer.append("null, ");
            } else if (isPrimitive(value.getClass())) {
                buffer.append(value + " ,");
            } else {
                buffer.append(value.toString() + " ,");
            }
        }
        log.info(buffer.toString());
    }
}
