package com.mufeng.enums;

/**
 * @description: 枚举表示是否有枚举
 * @Author: my.yang
 * @Date: 2020/4/2 10:29 PM
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
