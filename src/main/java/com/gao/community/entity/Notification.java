package com.gao.community.entity;

import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 18:53
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerid;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
