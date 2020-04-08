package com.mufeng.mapper;

import com.mufeng.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品评价自定义Mapper
 */
public interface ItemsMapperCustom {

    /**
     * 根据商品id查询商品评价
     *
     * @param map
     * @return
     */
    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);
}