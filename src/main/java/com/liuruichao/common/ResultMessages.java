package com.liuruichao.common;

/**
 * json数据信息
 *
 * @author liuruichao
 * @date 15/9/5 下午2:22
 */
public interface ResultMessages {
    String SYSTEM_ERROR     = "系统错误，请稍后再试！";
    String OPERATOR_ERROR   = "操作失败。";
    String OPERATOR_SUCCESS = "操作成功。";
    String SESSIONID_IS_NULL = "当前处于非法会话.";
}
