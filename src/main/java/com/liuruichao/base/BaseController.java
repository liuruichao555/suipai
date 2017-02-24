package com.liuruichao.base;


import com.liuruichao.common.Result;

public class BaseController {
    protected static final String ERROR_PAGE_404 = "errors/404";
    protected static final String ERROR_PAGE_500 = "errors/404";
    protected static final String LOGIN_USER_SESSION_KEY = "userInfo";

    protected <E> Result<E> newSuccessResult(String message, E data) {
        Result<E> result = new Result<>();
        result.setStatus(1);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    protected <E> Result<E> newFaildResult(String message) {
        Result<E> result = new Result<>();
        result.setStatus(-1);
        result.setMessage(message);
        return result;
    }
}
