package com.mufeng.service;

import com.mufeng.bo.SubmitOrderBO;

/**
 * @description: 订单服务
 * @Author: my.yang
 * @Date: 2020/4/5 1:45 PM
 */
public interface OrderService {

    /**
     * 创建订单相关信息
     *
     * @param submitOrderBO
     * @return
     */
    public String createOrder(SubmitOrderBO submitOrderBO);
}
