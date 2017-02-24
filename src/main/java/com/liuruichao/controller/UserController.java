package com.liuruichao.controller;

import com.liuruichao.base.BaseController;
import com.liuruichao.common.Result;
import com.liuruichao.common.ResultMessages;
import com.liuruichao.controller.vo.UserVO;
import com.liuruichao.listener.MySessionListener;
import com.liuruichao.model.User;
import com.liuruichao.service.IUserService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * UserController
 * 
 * @author liuruichao
 * Created on 2016-01-15 10:08
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private final Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private IUserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Result<UserVO> login(
            @RequestParam String mobile,
            @RequestParam String password,
            HttpServletRequest request) {
        Result<UserVO> result = null;
        try {
            if (StringUtils.isBlank(mobile) || mobile.length() != 11) {
                return newFaildResult("手机号必须是11位.");
            }
            if (StringUtils.isBlank(password) || password.length() < 6) {
                return newFaildResult("密码必须大于6位.");
            }
            User loginUser = userService.login(mobile, password);
            if (loginUser == null) {
                return newFaildResult("用户名密码错误.");
            }
            loginUser.setPassword(null);
            UserVO userVO = new UserVO(loginUser);
            userVO.setSessionId(request.getSession().getId());
            request.getSession().setAttribute(LOGIN_USER_SESSION_KEY, userVO);
            result = newSuccessResult("登录成功", userVO);
        } catch (Exception e) {
            logger.error("UserController.login()", e);
            result = newFaildResult(ResultMessages.SYSTEM_ERROR);
        }
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result<UserVO> register(
            @RequestParam String sessionId,
            @RequestParam String randomCode,
            @RequestParam String mobile,
            @RequestParam String password,
            @RequestParam String userName,
            @RequestParam(defaultValue = "") String avatarUrl) {
        Result<UserVO> result = null;
        try {
            HttpSession session = MySessionListener.getSession(sessionId);
            if (session == null) {
                return newFaildResult(ResultMessages.SESSIONID_IS_NULL);
            }
            // 验证码
            String sendCodeName = "register_" + mobile + "_code";
            String sendCode = (String) session.getAttribute(sendCodeName);
            if (StringUtils.isBlank(sendCode)) {
                return newFaildResult("请发送验证后再注册.");
            }
            if (userService.exists(mobile)) {
                return newFaildResult("该手机号已经注册!");
            }
            if (!sendCode.equals(randomCode)) {
                return newFaildResult("验证码错误,请重新输入.");
            }
            if (StringUtils.isBlank(randomCode)) {
                return newFaildResult("验证码不得为空.");
            }
            if (mobile == null || mobile.length() != 11 || !StringUtils.isNumeric(mobile)) {
                return newFaildResult("手机号必须是11位数字.");
            }
            if (password == null || password.length() < 6) {
                return newFaildResult("密码必须大于6位.");
            }
            if (StringUtils.isBlank(userName)) {
                return newFaildResult("userName不能为空.");
            }
            User user = userService.register(mobile, password, userName, avatarUrl);
            // 验证码只能使用一次,删除验证码
            session.removeAttribute(sendCodeName);
            UserVO userVO = new UserVO(user);
            userVO.setSessionId(sessionId);
            session.setAttribute(LOGIN_USER_SESSION_KEY, userVO);
            result = newSuccessResult(ResultMessages.OPERATOR_SUCCESS, userVO);
        } catch (Exception e) {
            logger.error("UserController.register()", e);
            result = newFaildResult(ResultMessages.SYSTEM_ERROR);
        }
        return result;
    }
}
