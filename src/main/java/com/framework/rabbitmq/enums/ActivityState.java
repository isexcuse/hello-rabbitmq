package com.framework.rabbitmq.enums;

import lombok.Getter;

/**
 * @author XiongFeiYang
 * @description 活动状态
 * @createTime 2019-06-05 11:13
 **/
@Getter
public enum ActivityState {

    WAITSUBMIT(1, "待提交"),
    INAUDIT(2, "审核中"),
    INENROLL(3, "报名中"),
    NOTSTARTED(4, "未开始"),
    INPROGRESS(5, "进行中"),
    HASENDED(6, "已结束"),
    AUDITFAIL(7, "审核失败");

    private int code;
    private String desc;

    ActivityState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ActivityState getCode(Integer code) {
        for (ActivityState state : ActivityState.values()) {
            if (state.code == code) {
                return state;
            }
        }
        return null;
    }

}
