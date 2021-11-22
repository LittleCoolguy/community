package com.gao.community.controller;

import com.gao.community.dto.CommentDTO;
import com.gao.community.entity.JsonData;
import com.gao.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 11:01
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public JsonData postComment(@RequestBody CommentDTO commentDTO, HttpSession session){
        return commentService.insert(commentDTO,session);
    }
}
