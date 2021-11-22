package com.gao.community.dto;

import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:18
 *
 */
@Data
public class CommentDTO {
    private Long parentId;//评论对象的Id
    private String content;
    private Integer type;//1表示对象是问题，2~评论
    private Integer commentCount;//评论数
}
