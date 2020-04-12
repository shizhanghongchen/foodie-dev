package com.mufeng.mapper;

import com.mufeng.vo.ItemCommentVO;
import com.mufeng.vo.SearchItemsVO;
import com.mufeng.vo.ShopcartVO;
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

    /**
     * 搜索商品列表(分页)
     *
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 根据分类id搜索商品列表(分页)
     *
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 根据规格ids查询最新的购物车中商品数据(用于刷新渲染购物车中的商品数据)
     *
     * @param sepcIdsList
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List sepcIdsList);

    /**
     * 减少库存
     *
     * @param specId
     * @param pendingCounts
     * @return
     */
    public int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCounts") int pendingCounts);
}