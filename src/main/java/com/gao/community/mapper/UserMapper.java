package com.gao.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gao.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.stream.BaseStream;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 19:29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
