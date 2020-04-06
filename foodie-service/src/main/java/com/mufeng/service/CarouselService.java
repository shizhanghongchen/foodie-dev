package com.mufeng.service;

import com.mufeng.pojo.Carousel;

import java.util.List;

/**
 * @description: 轮播图接口
 * @Author: my.yang
 * @Date: 2020/4/5 1:45 PM
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     *
     * @param isShow
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);
}
