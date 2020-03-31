package com.mufeng.service.impl;

import com.mufeng.mapper.StuMapper;
import com.mufeng.pojo.Stu;
import com.mufeng.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: Test Demo
 * 事务传播 - Propagation
 * <p>
 * REQUIRED(使用最多) : 使用当前的事务，如果当前没有事务则自己新建一个事务,子方法是必须运行在一个事务中的;
 * 如果当前存在事务则加入这个事务，成为一个整体;
 * 举例：领导没饭吃，我有钱，我会自己买了自己吃；领导有的吃，会分给你一起吃;
 * <p>
 * SUPPORTS(使用最多) : 如果当前有事务则使用事务;如果当前没有事务则不使用事务;
 * 举例 : 领导没饭吃，我也没饭吃；领导有饭吃，我也有饭吃。
 * <p>
 * MANDATORY : 该传播属性强制必须存在一个事务,如果不存在则抛出异常;
 * 举例：领导必须管饭不管饭没饭吃,我就不乐意了,就不干了（抛出异常）
 * <p>
 * REQUIRES_NEW : 如果当前有事务则挂起该事务,并且自己创建一个新的事务给自己使用,如果当前没有事务，则同 REQUIRED
 * 举例 : 领导有饭吃我偏不要,我自己买了自己吃;
 * <p>
 * NOT_SUPPORTED : 如果当前有事务则把事务挂起,自己不适用事务去运行数据库操作;
 * 举例 : 领导有饭吃分一点给你,我太忙了放一边不吃;
 * <p>
 * NEVER : 如果当前有事务存在,则抛出异常
 * 举例 : 领导有饭给你吃我不想吃,我热爱工作并抛出异常;
 * <p>
 * NESTED : 如果当前有事务,则开启子事务(嵌套事务),嵌套事务是独立提交或者回滚,如果当前没有事务,则同REQUIRED;
 * 但是如果主事务提交,则会携带子事务一起提交;如果主事务回滚,则子事务会一起回滚。相反子事务异常,则父事务可以回滚或不回滚;
 * 举例 : 领导决策不对老板怪罪,领导带着小弟一同受罪,小弟出了差错领导可以推卸责任;
 * @Author: my.yang
 * @Date: 2020/3/28 12:46 PM
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setAge(19);
        stu.setName("jack");
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setAge(20);
        stu.setName("lucy");
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }
}
