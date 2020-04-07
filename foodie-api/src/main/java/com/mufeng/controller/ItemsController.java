package com.mufeng.controller;

import com.mufeng.pojo.Items;
import com.mufeng.pojo.ItemsImg;
import com.mufeng.pojo.ItemsParam;
import com.mufeng.pojo.ItemsSpec;
import com.mufeng.service.ItemService;
import com.mufeng.utils.JSONResult;
import com.mufeng.vo.CategoryVO;
import com.mufeng.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 首页展示相关接口
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    /**
     * 查询商品详情
     *
     * @param itemId
     * @return
     */
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(@ApiParam(name = "itemId", value = "商品id", required = true) @PathVariable String itemId) {
        // 如果为空直接返回
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        // 根据商品id查询详情
        Items items = itemService.queryItemById(itemId);
        // 根据商品id查询商品图片列表
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        // 根据商品id查询商品规格
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        // 根据商品id查询商品属性
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        // 拼装数据
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return JSONResult.ok(itemInfoVO);
    }
}
