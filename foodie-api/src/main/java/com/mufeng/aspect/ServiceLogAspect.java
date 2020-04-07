package com.mufeng.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 日志切面
 * AOP通知 :
 * 1. 前置通知 : 在方法调用之前通知;
 * 2. 后置通知 : 在方法正常调用之后通知;
 * 3. 环绕通知 : 在方法调用之前和之后都分别可以执行的通知;
 * 4. 异常通知 : 如果在方法调用过程中发生异常则执行通知;
 * 5. 最终通知 : 在方法调用之后执行;
 * @Author: my.yang
 * @Date: 2020/4/5 12:09 AM
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * Service层接口时间记录 : 切面表达式
     * execution : 代表所要执行的表达式主体;
     * 第一处(*) : * 代表方法返回类型表示所有类型;
     * 第二处(path) : 包名代表aop监控的类所在的包;
     * 第三处(..) : 代表该包以及其子包下的所有类方法;
     * 第四处(*) : 代表类名,*代表所有类;
     * 第五处( *(..) ) : *代表类中的方法名,(..)表示方法中的任何参数;
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.mufeng.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // ********************** Web Request Log Begin **********************
        log.info("====== 开始执行 {}.{} ======",
                // 记录正在执行的Service
                joinPoint.getTarget().getClass(),
                // 记录正在执行的方法
                joinPoint.getSignature().getName());
        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 执行目标Service
        Object result = joinPoint.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        // 记录时间差
        long takeTime = end - begin;
        // 执行时间判断
        if (takeTime > 3000) {
            log.error("====== 执行结束, 耗时: {} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束, 耗时: {} 毫秒 ======", takeTime);
        } else {
            log.info("====== 执行结束, 耗时: {} 毫秒 ======", takeTime);
        }
        return result;
    }
}
