package com.gao.community.service;

import com.gao.community.dto.CommentDTO;
import com.gao.community.entity.JsonData;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/9 16:52
 */
public interface CommentService {
    JsonData insert(CommentDTO commentDTO, HttpSession session);
}
