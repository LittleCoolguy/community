package com.gao.community.service;

import com.gao.community.dto.QuestionDTO;
import com.gao.community.entity.Question;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/7 12:51
 */
public interface QuestionService {
    void insertQuestion(Question question);

    void update(Question question);

    List<Question> findAll(String search, String tag, String sort);

    QuestionDTO findById(Long id);

    void incCommentCount(Long parentId);

    List<Question> findAllById(Long id);

    List<Question> searchQuestions(String search);
}
