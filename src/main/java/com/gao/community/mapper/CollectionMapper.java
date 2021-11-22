package com.gao.community.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gao.community.dto.CollectsDTO;
import com.gao.community.entity.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:47
 */
public interface CollectionMapper extends BaseMapper<Collection> {
    List<CollectsDTO> collects(@Param(value = "userId") Long id);
}
