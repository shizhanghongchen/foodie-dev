package com.mufeng.service;

import com.mufeng.pojo.Category;
import com.mufeng.vo.CategoryVO;
import com.mufeng.vo.NewItemsVO;

import java.util.List;

/**
 * @description: 获取商品分类
 * @Author: my.yang
 * @Date: 2020/4/5 1:45 PM
 */
public interface CategoryService {

    /**
     * 获取商品分类(一级分类)
     *
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类Id查询子分类信息
     *
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个以及分类下的6条最新商品数据
     *
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
