package com.gao.community.entity;

import lombok.Data;

/**
 * @description: 评论/回复实体类
 * @author: XiaoGao
 * @time: 2021/11/3 18:51
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private int commentCount;
}
