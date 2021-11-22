package com.gao.community.controller;

import com.gao.community.entity.Collection;
import com.gao.community.entity.JsonData;
import com.gao.community.entity.User;
import com.gao.community.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/8 10:36
 */
@Controller
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @ResponseBody
    @GetMapping("/collect")
    public JsonData collect(@RequestParam(value = "questionId")Long questionId, HttpSession session){
        User user=(User) session.getAttribute("user");
        if (user==null){
            return JsonData.buildError("用户未登录",401);
        }
        if (questionId==null){
            return JsonData.buildError("问题Id不存在",402);
        }
        Collection collection=new Collection();
        collection.setUserId(user.getId());
        collection.setQuestionId(questionId);
        collection.setGmtCreate(System.currentTimeMillis());
        collectionService.collect(collection);
        return JsonData.buildSuccess("收藏成功", 200);
    }

    /**
     * 查询用户是否收藏某个问题
     */
    @ResponseBody
    @GetMapping("/isCollect")
    public JsonData isCollect(@RequestParam(value = "questionId", required = false) Long questionId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return JsonData.buildError("用户未登录", 401);
        }
        if (questionId == null){
            return JsonData.buildError("问题id为空", 402);
        }
        Collection collection = new Collection();
        collection.setUserId(user.getId());
        collection.setQuestionId(questionId);
        if(collectionService.isCollect(collection) != null){
            return JsonData.buildSuccess("您已经收藏过此问题了", 200);
        }
        return JsonData.buildSuccess("还未收藏此问题",201);
    }

    @ResponseBody
    @GetMapping("/delCollect/{id}")
    public JsonData collect(@PathVariable(name = "id") Long id){
        int row = collectionService.delCollect(id);
        if(row == 1){
            return JsonData.buildSuccess("取消收藏成功", 200);
        }
        return JsonData.buildError("取消收藏失败");
    }
}
