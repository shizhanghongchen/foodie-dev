package com.mufeng.mapper;

import com.mufeng.vo.CategoryVO;

import java.util.List;

/**
 * 自定义Mapper
 */
public interface CategoryMapperCustom {

    /**
     * 获取子分类信息
     *
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);
}