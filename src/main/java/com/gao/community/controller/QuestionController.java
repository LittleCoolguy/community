package com.gao.community.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gao.community.cache.HotTagCache;
import com.gao.community.cache.TagCache;
import com.gao.community.dto.CommentDTO;
import com.gao.community.dto.QuestionDTO;
import com.gao.community.entity.Comment;
import com.gao.community.entity.Question;
import com.gao.community.entity.User;
import com.gao.community.schedule.HotTagTasks;
import com.gao.community.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/7 11:58
 */
@Controller
public class QuestionController {
    @Autowired
    HotTagTasks hotTagTasks;
    @Autowired
    QuestionService questionService;
    @Autowired
    HotTagCache hotTagCache;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";//thymeleaf模板，返回到publish.html
    }
    /**
     * post方法提交用户发起的问题
     * @return
     */
    @PostMapping("/publishQuestion")
    public String publishQuestion(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model,
            HttpSession session){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        String invalid=TagCache.filterInvalid(tag);
        if (StringUtils.isNotEmpty(invalid)){
            model.addAttribute("error","标签非法: "+invalid);
            return "publish";
        }
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setHeadImg(user.getHeadImg());
        question.setId(id);
        hotTagTasks.hotTagSchedule2(tag);

        if (question.getId()==null){
            //说明上面的参数id为空
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionService.insertQuestion(question);
        }
        else {
            //说明此次操作为问题修改
            question.setGmtModified(question.getGmtModified());
            //这能对吗？？
            questionService.update(question);
        }
        System.out.println("model中存入tags "+hotTagCache.getHots().size());
//        model.addAttribute("tags",hotTagCache.getHots());//热门标签
        session.setAttribute("tags",hotTagCache.getHots());
        return "redirect:/";
    }

    /**
     * 显示问题(可根据标签/排行榜/页数)
     * @param page
     * @param size
     * @param search 这个参数是？？
     * @param tag
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping
    public String hello(
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "tag",required = false) String tag,
            @RequestParam(name = "sort",defaultValue = "") String sort,
            Model model){
        PageHelper.startPage(page,size);
        List<Question> all=questionService.findAll(search,tag,sort);
        PageInfo<Question> pageInfo=new PageInfo<>(all);
        model.addAttribute("questions",pageInfo);
        model.addAttribute("tag",tag);
//        hotTagTasks.hotTagSchedule();
//        model.addAttribute("tags",hotTagCache.getHots());//热门标签
        model.addAttribute("search",search);
        model.addAttribute("sort",sort);
        return "index";
    }
    
    @RequestMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model, HttpSession session){
        QuestionDTO question=questionService.findById(id);
        //查找问题
        if (question==null){
            model.addAttribute("message","该问题不存在或已被删除");
            return "error";
        }
        model.addAttribute("question",question);

        List<CommentDTO> commentDTOS=new ArrayList<>();
        return "question";
    }
    @RequestMapping("/searchQuestions")
    public String searchQuestions(
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "search")String search,
            Model model){
        PageHelper.startPage(page,size);
        List<Question> list=questionService.searchQuestions(search);
        PageInfo<Question> pageInfo=new PageInfo<>(list);
        model.addAttribute("questions",pageInfo);
        model.addAttribute("tags",hotTagCache.getHots());
        return "index";
    }

}
