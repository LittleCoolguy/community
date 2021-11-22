package com.gao.community.controller;

import com.gao.community.dto.NotificationUserDTO;
import com.gao.community.entity.User;
import com.gao.community.enums.NotificationTypeEnum;
import com.gao.community.mapper.QuestionMapper;
import com.gao.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/10 11:10
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationUserDTO notificationDTO = notificationService.read(id, user);
        //通知更新为已读
        System.out.println(notificationDTO);

        if(notificationDTO == null){
            return "redirect:/";
        }
        /**
         * 跳转到通知所对应的问题。因为评论针对的对象(问题/评论)都可以在question.html中查看；
         */
        if(notificationDTO.getType() == NotificationTypeEnum.REPLY_QUESTION.getType() ||
                NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterid();
        }
        return null;
    }
}
