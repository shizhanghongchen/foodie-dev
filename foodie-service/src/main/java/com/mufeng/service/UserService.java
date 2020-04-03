package com.mufeng.service;


import com.mufeng.pojo.Users;
import com.mufeng.pojo.bo.UserBo;

/**
 * @description: User接口
 * @Author: my.yang
 * @Date: 2020/3/28 12:44 PM
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     *
     * @return
     */
    Users createUser(UserBo userBo);

    /**
     * 检索用户名和密码是否匹配，用于登录
     *
     * @param username
     * @param password
     * @return
     */
    Users queryUserForLogin(String username, String password);
}
