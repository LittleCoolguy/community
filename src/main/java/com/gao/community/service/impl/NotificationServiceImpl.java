package com.gao.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.gao.community.dto.NotificationUserDTO;
import com.gao.community.entity.Notification;
import com.gao.community.entity.User;
import com.gao.community.enums.NotificationStatusEnum;
import com.gao.community.enums.NotificationTypeEnum;
import com.gao.community.mapper.NotificationMapper;
import com.gao.community.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/5 14:27
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public Integer unreadCount(Long id) {
        Integer integer = notificationMapper.selectCount(new QueryWrapper<Notification>().eq("receiver", id).eq("status", NotificationStatusEnum.UNREAD));
        return integer;
    }

    @Override
    public List<NotificationUserDTO> findAllById(Long id) {
        List<NotificationUserDTO> list=new ArrayList<>();
        List<Notification> notifications = notificationMapper.selectList(new QueryWrapper<Notification>().eq("receiver", id).orderByAsc("status"));
        if (notifications==null)return list;
        for (Notification notification:notifications){
            NotificationUserDTO notificationUserDTO=new NotificationUserDTO();
            BeanUtils.copyProperties(notification,notificationUserDTO);
            notificationUserDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            list.add(notificationUserDTO);
        }
        return list;
    }
    
    @Override
    public NotificationUserDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification==null)return null;
        if (notification.getReceiver().equals(user.getId())==false)return null;
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);
        NotificationUserDTO notificationUserDTO=new NotificationUserDTO();
        BeanUtils.copyProperties(notification,notificationUserDTO);
        notificationUserDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationUserDTO;
    }
}
