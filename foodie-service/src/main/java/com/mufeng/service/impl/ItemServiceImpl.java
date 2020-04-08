package com.mufeng.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mufeng.enums.CommentLevel;
import com.mufeng.mapper.ItemsCommentsMapper;
import com.mufeng.mapper.ItemsImgMapper;
import com.mufeng.mapper.ItemsMapper;
import com.mufeng.mapper.ItemsMapperCustom;
import com.mufeng.mapper.ItemsParamMapper;
import com.mufeng.mapper.ItemsSpecMapper;
import com.mufeng.pojo.Items;
import com.mufeng.pojo.ItemsComments;
import com.mufeng.pojo.ItemsImg;
import com.mufeng.pojo.ItemsParam;
import com.mufeng.pojo.ItemsSpec;
import com.mufeng.service.ItemService;
import com.mufeng.utils.DesensitizationUtil;
import com.mufeng.utils.PagedGridResult;
import com.mufeng.vo.CommentLevelCountsVO;
import com.mufeng.vo.ItemCommentVO;
import com.mufeng.vo.SearchItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 获取商品分类实现
 * @Author: my.yang
 * @Date: 2020/4/7 3:12 PM
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    /**
     * 根据商品id查询详情
     *
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品属性
     *
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    /**
     * 根据商品id查询商品的评价等级数量
     *
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        countsVO.setGoodCounts(goodCounts);
        return countsVO;
    }

    /**
     * 根据商品id查询商品评价(分页)
     *
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        /**
         * mybatis-pagehelper : 使用分页插件，在查询前使用分页插件，原理：统一拦截sql，为其提供分页功能
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> resultList = itemsMapperCustom.queryItemComments(map);
        // 脱敏
        resultList.forEach(data -> data.setNickname(DesensitizationUtil.commonDisplay(data.getNickname())));
        /**
         * 返回分页数据
         */
        return setterPagedGrid(page, resultList);
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
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);
        // 开始分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> resultList = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(page, resultList);
    }

    /**
     * 根据分类id搜索商品列表(分页)
     *
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);
        // 开始分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> resultList = itemsMapperCustom.searchItemsByThirdCat(map);
        return setterPagedGrid(page, resultList);
    }

    /**
     * 评论等级通用查询
     *
     * @param itemId
     * @param level
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        // 设置查询条件
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        // 查询
        return itemsCommentsMapper.selectCount(condition);
    }

    /**
     * 分页数据封装到 PagedGridResult.java 传给前端
     *
     * @param page
     * @param list
     * @return
     */
    private PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
