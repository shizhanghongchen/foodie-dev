package com.mufeng.enums;

/**
 * @description: 性别枚举
 * @Author: my.yang
 * @Date: 2020/4/2 10:29 PM
 */
public enum Sex {
    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
