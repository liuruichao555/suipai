package com.liuruichao.service;

import com.liuruichao.model.User;

import java.io.File;

/**
 * IUserService
 * 
 * @author liuruichao
 * Created on 2016-01-15 10:18
 */
public interface IUserService {
    User register(String mobile, String password, String userName, String avatarUrl);
    User register(String mobile, String password, String userName, byte[] avatarData, String fileName);
    User login(String mobile, String password);
    boolean exists(String mobile);
}