package com.liuruichao.common;

import java.io.Serializable;

/**
 * json 数据实体类
 * 
 * @author liuruichao
 * 
 */
public final class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private T data;

    public Result() {
    }
	
	public Result(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
