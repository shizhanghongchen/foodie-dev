package com.mufeng.controller;

import org.springframework.stereotype.Controller;

/**
 * @description: 通用Cotroller
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@Controller
public class BaseController {

    /**
     * 评论分页数量
     */
    public static final Integer COMMENT_PAGE_SIZE = 10;

    /**
     * 商品分页数量
     */
    public static final Integer ITEM_PAGE_SIZE = 20;

    /**
     * 分页页码
     */
    public static final Integer COMMENT_PAGE = 1;

    /**
     * 购物车标识
     */
    public static final String FOODIE_SHOPCART = "shopcart";
}
