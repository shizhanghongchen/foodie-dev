package com.mufeng.controller;

import com.mufeng.pojo.Users;
import com.mufeng.bo.UserBo;
import com.mufeng.service.UserService;
import com.mufeng.utils.CookieUtils;
import com.mufeng.utils.JSONResult;
import com.mufeng.utils.JsonUtils;
import com.mufeng.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 用户模块
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@Api(value = "注册登录", tags = "用户模块相关接口")
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {
        // 用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }
        // 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        // 判断成功用户名没有重复
        return JSONResult.ok();
    }

    /**
     * 用户注册
     *
     * @param userBo
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBo userBo, HttpServletRequest request,
                             HttpServletResponse response) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        // 1. 判断用户名和密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2. 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        // 3. 判断用户密码不能少于6位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }
        // 4. 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        // 5. 注册
        Users userResult = userService.createUser(userBo);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        // todo : 生成用户token并存入redis会话
        // todo : 同步购物车数据
        return JSONResult.ok();
    }

    /**
     * 用户登录
     *
     * @param userBo
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBo userBo, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        // 1. 判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2. 实现登录
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        // 3. 账号密码校验
        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        // todo : 生成用户token并存入redis会话
        // todo : 同步购物车数据
        return JSONResult.ok();
    }

    /**
     * 用户退出登录
     *
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        // 1. 清除用户相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");
        // todo : 用户退出登录需要清空购物车;
        // todo : 分布式会话中需要清除用户数据;
        return JSONResult.ok();
    }

    /**
     * 设置用户对象
     *
     * @param userResult
     */
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}
