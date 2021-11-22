package com.gao.community.entity;

import com.gao.community.enums.LikedStatusEnum;
import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 18:55
 */
@Data
public class UserLike {
    private Integer id;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public UserLike() {
    }

    public UserLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}
