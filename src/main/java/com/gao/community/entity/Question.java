package com.gao.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 18:53
 */
@Data
public class Question {
    @TableId(type = IdType.AUTO)
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
}
