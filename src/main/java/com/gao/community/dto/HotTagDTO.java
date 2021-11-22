package com.gao.community.dto;

import lombok.Data;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/7 13:19
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
