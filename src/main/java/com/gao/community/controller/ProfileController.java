package com.gao.community.controller;

import com.gao.community.dto.CollectsDTO;
import com.gao.community.dto.NotificationUserDTO;
import com.gao.community.entity.Question;
import com.gao.community.entity.User;
import com.gao.community.service.CollectionService;
import com.gao.community.service.NotificationService;
import com.gao.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:57
 */
@Controller
public class ProfileController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CollectionService collectionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "10")Integer size,
                          HttpSession session,
                          Model model){
        User user=(User)session.getAttribute("user");
        if (user==null)
            return "redirect:/";
        if ("replies".equals(action)){
            //查看信息
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PageHelper.startPage(page,size);
            List<NotificationUserDTO> all=notificationService.findAllById(user.getId());
            PageInfo<NotificationUserDTO> pageInfo=new PageInfo<>(all);
            model.addAttribute("pageInfo",pageInfo);
        }
        else if ("questions".equals(action)){
            //查看《我的问题》
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题/内容");
            PageHelper.startPage(page,size);
            List<Question> questionList=questionService.findAllById(user.getId());
            PageInfo<Question> questionPageInfo=new PageInfo<>(questionList);
            model.addAttribute("pageInfo",questionPageInfo);
        }
        else if ("collects".equals(action)){
            model.addAttribute("section","collects");
            model.addAttribute("sectionName","我的收藏");
            PageHelper.startPage(page,size);
            List<CollectsDTO> all = collectionService.findAll(user.getId());
            PageInfo<CollectsDTO> questionPageInfo=new PageInfo<>(all);
            model.addAttribute("pageInfo",questionPageInfo);
        }
        return "profile";
    }
}
