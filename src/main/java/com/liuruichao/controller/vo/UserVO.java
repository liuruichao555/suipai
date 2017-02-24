package com.liuruichao.controller.vo;

import com.liuruichao.model.User;

import java.io.Serializable;

/**
 * UserVO
 *
 * @author liuruichao
 * Created on 2016-01-21 14:55
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String mobile;
    private String userName;
    private String avatarUrl; // 头像
    private Integer gender;
    private Integer age;
    private Integer photoCount;// 照片数量
    private Integer collectCount;// 收藏数量
    private Integer followCount;// 关注数量
    private Integer fansCount;// 粉丝数量
    private String sessionId;

    public UserVO(User user) {
        this.userId = user.getUserId();
        this.mobile = user.getMobile();
        this.userName = user.getUserName();
        this.avatarUrl = user.getAvatarUrl();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.photoCount = user.getPhotoCount();
        this.collectCount = user.getCollectCount();
        this.followCount = user.getFollowCount();
        this.fansCount = user.getFansCount();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }
}
