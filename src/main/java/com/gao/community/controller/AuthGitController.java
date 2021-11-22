package com.gao.community.controller;

import com.gao.community.dto.AccessTokenDTO;
import com.gao.community.dto.GithubUser;
import com.gao.community.entity.User;
import com.gao.community.service.UserService;
import com.gao.community.utils.Constants;
import com.gao.community.utils.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 19:27
 */
@Controller
@Slf4j
public class AuthGitController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @RequestMapping("/demo")
    @ResponseBody
    public String demo(){
        return "你好,world";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpSession session){
        System.out.println("到了callback");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Constants.clientId);
        accessTokenDTO.setClient_secret(Constants.clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Constants.redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            //获取User成功，封装User对象
            User user=new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setHeadImg(githubUser.getAvatar_url());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            User findUser=userService.findByAccountId(user.getAccountId());
            if (findUser==null){
                //之前没有过
                userService.insert(user);
            }
            else {
                user.setId(findUser.getId());
                userService.update(user);
            }
            session.setAttribute("user",user);
            Cookie cookie=new Cookie("token",token);
            System.out.println("cookie: "+token);
            cookie.setMaxAge(60*60*3);
            response.addCookie(cookie);
            userService.autoLogin(user);
            return "redirect:/";
        }
        else {
            log.error("callback get github user error,{}",githubUser);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        userService.logout(request,response);
        return "redirect:/";
    }
}
