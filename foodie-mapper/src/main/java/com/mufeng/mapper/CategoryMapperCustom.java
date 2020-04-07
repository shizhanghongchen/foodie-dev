package com.mufeng.mapper;

import com.mufeng.vo.CategoryVO;
import com.mufeng.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询首页每个以及分类下的6条最新商品数据
     *
     * @param map
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}