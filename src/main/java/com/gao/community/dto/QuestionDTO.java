package com.gao.community.dto;

import com.gao.community.entity.User;
import lombok.Data;

/**
 * @description: 问题的相关信息
 * @author: XiaoGao
 * @time: 2021/11/7 12:09
 */
@Data
public class QuestionDTO {
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
    private Integer choice;
    private User user;
}
