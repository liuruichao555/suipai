package com.liuruichao.service.impl;

import com.google.gson.Gson;
import com.liuruichao.mapper.UserMapper;
import com.liuruichao.model.User;
import com.liuruichao.remote.vo.FileUploadResult;
import com.liuruichao.remote.vo.UploadResult;
import com.liuruichao.service.IUserService;
import com.liuruichao.utils.UploadImageUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserServiceImpl
 * 
 * @author liuruichao
 * Created on 2016-01-15 10:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    private Gson gson = new Gson();

    @Override
    public User register(String mobile, String password, String userName, String avatarUrl) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("mobile参数不得为空.");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password参数不得为空.");
        }
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException("userName参数不得为空.");
        }

        User user = new User();
        user.setAge(0);
        user.setAvatarUrl(avatarUrl);
        user.setGender(0);
        user.setMobile(mobile);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setStatus(1);
        user.setUserName(userName);
        user.setRegisterTime(new Date());
        int userId = userMapper.saveUser(user);
        user.setUserId(userId);
        return user;
    }

    @Override
    public User register(String mobile, String password, String userName, byte[] avatarData, String fileName) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("mobile参数不得为空.");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password参数不得为空.");
        }
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException("userName参数不得为空.");
        }
        if (avatarData == null || avatarData.length <= 0) {
            throw new IllegalArgumentException("avatar参数不得为空.");
        }

        User user = null;
        // 上传文件
        try {
            List<byte[]> avatarDataList = new ArrayList<>(1);
            avatarDataList.add(avatarData);
            String jsonStr = UploadImageUtils.uploadImage(avatarDataList, new String[] { fileName });
            UploadResult uploadResult = gson.fromJson(jsonStr, UploadResult.class);
            if (uploadResult.getStatus() == 1) {
                List<FileUploadResult> fileUploadResult = uploadResult.getData();
                if (fileUploadResult != null && fileUploadResult.size() > 0) {
                    // 因为只有一个头像
                    FileUploadResult avatarResult = fileUploadResult.get(0);
                    String avatarUrl = avatarResult.getUrl();
                    user = register(mobile, password, userName, avatarUrl);
                }
            } else {
                throw new RuntimeException("上传头像失败,请检查图片服务器.");
            }
        } catch (IOException e) {
            logger.error("UserServiceImpl.register()", e);
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User login(String mobile, String password) {
        User loginUser = null;
        User dbUser = userMapper.getUserByMobile(mobile);
        if (dbUser != null && dbUser.getPassword().equals(DigestUtils.md5Hex(password))) {
            loginUser = dbUser;
        }
        return loginUser;
    }

    @Override
    public boolean exists(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("mobile参数不得为空.");
        }
        int count = userMapper.getUserNum(mobile);
        return count > 0;
    }
}
