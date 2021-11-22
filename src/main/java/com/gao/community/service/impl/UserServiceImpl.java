package com.gao.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gao.community.entity.User;
import com.gao.community.mapper.UserMapper;
import com.gao.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 19:29
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public User findByAccountId(String id) {
        QueryWrapper<User> o = new QueryWrapper<>();
        o.eq("account_id", id);
        return userMapper.selectOne(o);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void autoLogin(User user) {
        System.out.println("redis存入");
        System.out.println(user);
        System.out.println("user: "+user.getToken());
        redisTemplate.opsForValue().set(user.getToken(), user,60*60*24*3, TimeUnit.SECONDS);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        System.out.println("用户退出登录");
        Cookie[] cookies=request.getCookies();
        if (cookies!=null||cookies.length!=0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    redisTemplate.delete(token);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
//    为啥可以传一个User对象
}
