package com.gao.community.enums;

import lombok.Getter;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 18:56
 */
@Getter
public enum  LikedStatusEnum {
    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞"),
    ;

    private Integer code;
    private String msg;
    LikedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
