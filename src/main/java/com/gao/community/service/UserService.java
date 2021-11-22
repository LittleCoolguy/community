package com.gao.community.service;

import com.gao.community.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 19:28
 */

public interface UserService {
    User findByAccountId(String id);

    void insert(User user);


    void autoLogin(User user);

    void update(User user);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
