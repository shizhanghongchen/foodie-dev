package com.mufeng.service;

import com.mufeng.pojo.Stu;

/**
 * @description: Test Demo
 * @Author: my.yang
 * @Date: 2020/3/28 12:44 PM
 */
public interface StuService {

    public Stu getStuInfo(int id);

    public void saveStu();

    public void updateStu(int id);

    public void deleteStu(int id);
}
