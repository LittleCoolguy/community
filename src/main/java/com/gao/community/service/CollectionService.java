package com.gao.community.service;

import com.gao.community.dto.CollectsDTO;
import com.gao.community.entity.Collection;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:44
 */
public interface CollectionService {
    void collect(Collection collection);

    Collection isCollect(Collection collection);

    int delCollect(Long id);

    List<CollectsDTO> findAll(Long id);
}
