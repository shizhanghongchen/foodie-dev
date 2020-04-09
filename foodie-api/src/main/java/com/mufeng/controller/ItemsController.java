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
import com.mufeng.vo.ShopcartVO;
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
     * 查询商品评价(分页)
     *
     * @param itemId
     * @return
     */
    @ApiOperation(value = "查询商品评价(分页)", notes = "查询商品评价(分页)", httpMethod = "GET")
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

    /**
     * 搜索商品列表(分页)
     *
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "搜索商品列表(分页)", notes = "搜索商品列表(分页)", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam String keywords,
                             @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
                             @ApiParam(name = "page", value = "查询下一页的地址页", required = false) @RequestParam Integer page,
                             @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {
        // 如果为空直接返回
        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = COMMENT_PAGE;
        }
        if (pageSize == null) {
            pageSize = ITEM_PAGE_SIZE;
        }
        PagedGridResult result = itemService.searchItems(keywords, sort, page, pageSize);
        return JSONResult.ok(result);
    }

    /**
     * 搜索商品列表(分页)
     *
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "通过分类id搜索商品列表(分页)", notes = "通过分类id搜索商品列表(分页)", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(@ApiParam(name = "catId", value = "三级分类id", required = true) @RequestParam Integer catId,
                               @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
                               @ApiParam(name = "page", value = "查询下一页的地址页", required = false) @RequestParam Integer page,
                               @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Integer pageSize) {
        // 如果为空直接返回
        if (catId == null) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = COMMENT_PAGE;
        }
        if (pageSize == null) {
            pageSize = ITEM_PAGE_SIZE;
        }
        // 可重载
        PagedGridResult result = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return JSONResult.ok(result);
    }

    /**
     * 用于用户长时间未登录网站,刷新购物车中的数据(商品价格),类似于京东/淘宝
     *
     * @param itemSpecIds
     * @return
     */
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
                              @RequestParam String itemSpecIds) {
        // 如果为空直接返回
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        List<ShopcartVO> result = itemService.queryItemsBySpecIds(itemSpecIds);
        return JSONResult.ok(result);
    }
}
