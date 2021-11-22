package com.gao.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 17:14
 */
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private String headImg;
    private Integer status;

}
