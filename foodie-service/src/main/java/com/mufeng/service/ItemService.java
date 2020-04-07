package com.mufeng.service;

import com.mufeng.pojo.Items;
import com.mufeng.pojo.ItemsImg;
import com.mufeng.pojo.ItemsParam;
import com.mufeng.pojo.ItemsSpec;

import java.util.List;

/**
 * @description: 商品接口
 * @Author: my.yang
 * @Date: 2020/4/5 1:45 PM
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     *
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品属性
     *
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);
}
