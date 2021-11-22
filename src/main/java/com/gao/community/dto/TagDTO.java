package com.gao.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/7 12:00
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
