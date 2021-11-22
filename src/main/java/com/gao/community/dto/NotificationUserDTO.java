package com.gao.community.dto;

import com.gao.community.entity.User;
import lombok.Data;

/**
 * @description: 返回问题的所有评论列表，以及评论人的信息
 * @author: XiaoGao
 * @time: 2021/11/10 10:48
 */
@Data
public class NotificationUserDTO {
    private Long id;
    private Long notifier;
    private Long outerid;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String typeName;
}
