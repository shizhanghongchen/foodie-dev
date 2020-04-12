package com.mufeng.controller;

import com.mufeng.bo.SubmitOrderBO;
import com.mufeng.enums.PayMethod;
import com.mufeng.service.OrderService;
import com.mufeng.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 订单相关Controller
 * @Author: my.yang
 * @Date: 2020/4/12 9:35 AM
 */
@Api(value = "订单相关的Api接口", tags = {"订单相关的Api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据用户的id查询用户的收货地址列表
     *
     * @param submitOrderBO
     * @return
     */
    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return JSONResult.errorMsg("支付方式不支持!");
        }
        System.out.println(submitOrderBO.toString());
        // 1. 创建订单
        String orderId = orderService.createOrder(submitOrderBO);
        // 2. 创建订单以后移除购物车中已结算的商品
        // todo 整合redis之后,完善购物车中的已结算商品清除,并且同步到前端的cookie
        // CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        // 3. 向支付中心发送当前订单,用于保存支付中心的订单数据
        return JSONResult.ok(orderId);
    }
}
