package com.gao.community.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gao.community.dto.CommentDTO;
import com.gao.community.entity.*;
import com.gao.community.mapper.CommentMapper;
import com.gao.community.mapper.NotificationMapper;
import com.gao.community.mapper.QuestionMapper;
import com.gao.community.service.CommentService;
import com.gao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/9 16:52
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public JsonData insert(CommentDTO commentDTO, HttpSession session) {
        User user=(User) session.getAttribute("user");
        if (user==null){
            return JsonData.buildError("尚未登录，别想评论！");
        }
        if (commentDTO==null|| StrUtil.isEmpty(commentDTO.getContent())){
            return JsonData.buildError("评论不能为空的，多少写点");
        }
        if (commentDTO.getType()==null){
            return JsonData.buildError("评论啥，再想想？");
        }
        //type为2表示回复的评论，判断评论是否存在
        if (commentDTO.getType().equals(2)){
            Comment dbComment = commentMapper.selectById(commentDTO.getParentId());
            if (dbComment == null){
                return JsonData.buildError("您回复的评论不存在或已被删除!");
            }
        }

        //type为1表示回复的问题，判断问题是否存在
        if (commentDTO.getType().equals(1)){
            Question question = questionMapper.selectById(commentDTO.getParentId());
            if (question == null){
                return JsonData.buildError("您回复的问题不存在或已被删除!");
            }
        }
        Comment comment=new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        questionService.incCommentCount(commentDTO.getParentId());//评论数+1

        commentMapper.insert(comment);
        //新增通知
        Question question=questionMapper.selectById(commentDTO.getParentId());
        if (question!=null){
            createNotify(comment,question.getTitle(),user.getName(),question.getId());
        }
        else {
            Comment comment1=commentMapper.selectById(commentDTO.getParentId());
            createNotify(comment,comment.getContent(),user.getName(),comment1.getId());
        }
        return JsonData.buildSuccess("成功",comment.getContent().toString());
    }
    private void createNotify(Comment comment,String title,String NotifierName,Long outerId ){
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterTitle(title);
        notification.setReceiver(comment.getCommentator());
        notification.setType(comment.getType());
        notification.setStatus(0);
        notification.setNotifierName(NotifierName);
        notification.setOuterid(outerId);
        notificationMapper.insert(notification);
    }
}
