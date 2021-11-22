package com.gao.community.dto;

import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 20:09
 */
@Data
public class GithubUser {
    private Long id;
    private String name;
    //描述
    private String bio;
    //github头像
    private String avatar_url;
}
