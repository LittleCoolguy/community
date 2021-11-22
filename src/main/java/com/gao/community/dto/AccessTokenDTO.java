package com.gao.community.dto;

import lombok.Data;

/**
 * @description: Github获取access+token需要传输的对象类
 * @author: XiaoGao
 * @time: 2021/11/3 20:05
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
