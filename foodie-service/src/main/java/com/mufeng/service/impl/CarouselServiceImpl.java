package com.mufeng.service.impl;

import com.mufeng.mapper.CarouselMapper;
import com.mufeng.pojo.Carousel;
import com.mufeng.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @description: 轮播图实现类
 * @Author: my.yang
 * @Date: 2020/4/5 1:47 PM
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询所有轮播图列表
     *
     * @param isShow
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
