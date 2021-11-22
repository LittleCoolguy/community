package com.gao.community.dto;

import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/10 16:48
 */
@Data
public class CollectsDTO {
    private Long collectId;
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private String headImg;
    private Long gmtCreate2;
}
