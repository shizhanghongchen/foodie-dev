package com.mufeng.service.impl;

import com.mufeng.enums.CatsEnums;
import com.mufeng.mapper.CategoryMapper;
import com.mufeng.mapper.CategoryMapperCustom;
import com.mufeng.pojo.Category;
import com.mufeng.service.CategoryService;
import com.mufeng.vo.CategoryVO;
import com.mufeng.vo.NewItemsVO;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    /**
     * 获取商品分类(一级分类)
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", CatsEnums.ONE.type);
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }

    /**
     * 根据一级分类Id查询子分类信息
     *
     * @param rootCatId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List<CategoryVO> subCatList = categoryMapperCustom.getSubCatList(rootCatId);
        return subCatList;
    }

    /**
     * 查询首页每个以及分类下的6条最新商品数据
     *
     * @param rootCatId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        List<NewItemsVO> sixNewItemsLazy = categoryMapperCustom.getSixNewItemsLazy(map);
        return sixNewItemsLazy;
    }
}
