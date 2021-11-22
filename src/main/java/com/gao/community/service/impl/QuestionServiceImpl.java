package com.gao.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gao.community.dto.QuestionDTO;
import com.gao.community.entity.Comment;
import com.gao.community.entity.Question;
import com.gao.community.entity.User;
import com.gao.community.enums.SortEnum;
import com.gao.community.mapper.CommentMapper;
import com.gao.community.mapper.QuestionMapper;
import com.gao.community.mapper.UserMapper;
import com.gao.community.service.QuestionService;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/7 12:52
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Override
    public void insertQuestion(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void update(Question question) {
        questionMapper.updateById(question);
    }

    @Override
    public List<Question> findAll(String search,String tag,String sort) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (!StringUtil.isEmpty(search)){//不为空时
            wrapper.like("title", search);
            //=> title like %search%
        }
        if (!StringUtil.isEmpty(tag)){
            wrapper.like("tag", tag);
        }
        if (SortEnum.NO.name().toLowerCase().equals(sort)){
            wrapper.orderByDesc("gmt_create").eq("comment_count", 0);
            //降序排列
            return questionMapper.selectList(wrapper);
        }

        if (StringUtil.isEmpty(sort)){
            wrapper.orderByDesc("gmt_create");
            return questionMapper.selectList(wrapper);
        }

        if (SortEnum.CHOICE.name().toLowerCase().equals(sort)){
            System.out.println("进入精华");
            wrapper.orderByDesc("gmt_create").eq("choice", 1);
            return questionMapper.selectList(wrapper);
        }

        wrapper.orderByDesc("view_count","comment_count");
        if (SortEnum.HOT7.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
        }

        if (SortEnum.HOT30.name().toLowerCase().equals(sort)){
            wrapper.gt("gmt_create", System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
        }

        return questionMapper.selectList(wrapper);

    }

    @Override
    public QuestionDTO findById(Long id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.selectById(id);
        question.setViewCount(question.getViewCount()+1);
        questionMapper.updateById(question);
        //查询问题并更新访问量
        User user=userMapper.selectById(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     *
     * 1.评论数+1
     * @param parentId
     */
    @Override
    public void incCommentCount(Long parentId) {
        Question question=questionMapper.selectById(parentId);
        if (question!=null){
            question.setCommentCount(question.getCommentCount()+1);
            questionMapper.updateById(question);
        }
        else {
            Comment comment=commentMapper.selectById(parentId);
            comment.setCommentCount(comment.getCommentCount()+1);
            commentMapper.updateById(comment);
        }
    }

    @Override
    public List<Question> findAllById(Long id) {
        Map<String,Object> map=new HashMap<>();
        map.put("creator",id);
        return questionMapper.selectByMap(map);
    }

    @Override
    public List<Question> searchQuestions(String search) {
        return questionMapper.selectList(new QueryWrapper<Question>().like("title",search).orderByDesc("id"));
    }
}
