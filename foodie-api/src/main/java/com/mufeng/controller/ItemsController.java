package com.mufeng.controller;

import com.mufeng.pojo.Items;
import com.mufeng.pojo.ItemsImg;
import com.mufeng.pojo.ItemsParam;
import com.mufeng.pojo.ItemsSpec;
import com.mufeng.service.ItemService;
import com.mufeng.utils.JSONResult;
import com.mufeng.utils.PagedGridResult;
import com.mufeng.vo.CommentLevelCountsVO;
import com.mufeng.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class ItemsController extends BaseController {

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

    /**
     * 查询商品评价等级
     *
     * @param itemId
     * @return
     */
    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId) {
        // 如果为空直接返回
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        // 根据商品id查询评价等级
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return JSONResult.ok(countsVO);
    }

    /**
     * 查询商品评价
     *
     * @param itemId
     * @return
     */
    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(@ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId,
                               @ApiParam(name = "level", value = "评价等级", required = false) @RequestParam Integer level,
                               @ApiParam(name = "page", value = "查询下一页的地址页", required = false) @RequestParam Integer page,
                               @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {
        // 如果为空直接返回
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = COMMENT_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult result = itemService.queryPagedComments(itemId, level, page, pageSize);
        return JSONResult.ok(result);
    }
}
