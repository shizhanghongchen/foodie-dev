package com.mufeng.enums;

/**
 * @description:
 * @Author: my.yang
 * @Date: 2020/4/7 3:14 PM
 */
public enum CatsEnums {

    ONE(1, "one"),
    TWO(2, "two"),
    THREE(3, "THREE");

    public final Integer type;
    public final String value;

    CatsEnums(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
