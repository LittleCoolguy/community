package com.gao.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 18:55
 */
@Data
public class Report {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long questionId;
    private String questionTitle;
    private String reportType;
    private String reportDetails;
    private Long reportCreator;
    private String reportUser;
    private Long gmtCreate;
    private Integer reportStatus;
}
