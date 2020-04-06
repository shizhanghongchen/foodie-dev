package com.mufeng.controller;

import com.mufeng.enums.YesOrNo;
import com.mufeng.pojo.Carousel;
import com.mufeng.service.CarouselService;
import com.mufeng.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @description: 首页展示相关接口
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@Api(value = "首页", tags = "首页展示相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 首页轮播图展示接口
     *
     * @return
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(carousels);
    }
}
