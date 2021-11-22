package com.gao.community.service;

import com.gao.community.dto.NotificationUserDTO;
import com.gao.community.entity.User;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/5 14:27
 */
public interface NotificationService {
    Integer unreadCount(Long id);

    List<NotificationUserDTO> findAllById(Long id);

    NotificationUserDTO read(Long id, User user);
}
