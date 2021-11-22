package com.gao.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gao.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/9 17:02
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
