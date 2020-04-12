package com.mufeng.service;

import com.mufeng.bo.AddressBO;
import com.mufeng.pojo.UserAddress;

import java.util.List;

/**
 * @description: 收货地址
 * @Author: my.yang
 * @Date: 2020/4/5 1:45 PM
 */
public interface AddressService {

    /**
     * 根据用户的id查询用户的收货地址列表
     *
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 用户新增地址
     *
     * @param addressBO
     * @return
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     *
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id删除对应的用户地址信息
     *
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     *
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId, String addressId);
}
