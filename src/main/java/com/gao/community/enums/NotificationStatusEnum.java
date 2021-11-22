package com.gao.community.enums;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/5 14:39
 */
public enum  NotificationStatusEnum {
    /**
     * 0表示未读
     * 1表示已读
     */
    UNREAD(0),READ(1);
    private int status;

    public int getStatus(){
        return status;
    }
    NotificationStatusEnum(int status){
        this.status=status;
    }
}
