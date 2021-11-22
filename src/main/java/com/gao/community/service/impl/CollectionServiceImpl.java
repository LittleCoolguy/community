package com.gao.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gao.community.dto.CollectsDTO;
import com.gao.community.entity.Collection;
import com.gao.community.mapper.CollectionMapper;
import com.gao.community.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:45
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;
    @Override
    public void collect(Collection collection) {
        collectionMapper.insert(collection);
    }

    @Override
    public Collection isCollect(Collection collection) {
        QueryWrapper<Collection> wrapper=new QueryWrapper<>();
        wrapper.eq("question_id",collection.getQuestionId());
        wrapper.eq("user_id", collection.getUserId());
        return collectionMapper.selectOne(wrapper);
    }

    @Override
    public int delCollect(Long id) {
        return collectionMapper.deleteById(id);
    }

    @Override
    public List<CollectsDTO> findAll(Long id) {
        return collectionMapper.collects(id);
    }
}
