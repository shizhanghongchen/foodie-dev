package com.mufeng.controller;

import com.mufeng.bo.ShopcartBO;
import com.mufeng.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 购物车模块Controller
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@Api(value = "购物车接口Controller", tags = "购物车接口相关的Controller")
@RequestMapping("shopcart")
@RestController
public class ShopCartController {
    // shopcart

    /**
     * 同步购物车到后端
     *
     * @param userId
     * @param shopcartBO
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "同步购物车到后端", notes = "同步购物车到后端", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO, HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        // todo : 前端用户在登录的情况下添加商品到购物车会同时在后端同步购物车到Redis缓存
        return JSONResult.ok();
    }

    /**
     * 从购物车中删除商品
     *
     * @param userId
     * @param itemSpecId
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId, @RequestParam String itemSpecId, HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }
        // todo : 用户在页面删除购物车中的商品数据,如果此时用户已经登录,则需要同步删除后端购物车中的商品;
        return JSONResult.ok();
    }
}
