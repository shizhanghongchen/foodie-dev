package com.mufeng.enums;

/**
 * @description: 评论等级枚举
 * @Author: my.yang
 * @Date: 2020/4/2 10:29 PM
 */
public enum CommentLevel {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评");

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
