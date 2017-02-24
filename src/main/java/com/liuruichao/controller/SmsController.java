package com.liuruichao.controller;

import com.liuruichao.base.BaseController;
import com.liuruichao.common.Result;
import com.liuruichao.common.ResultMessages;
import com.liuruichao.config.SmsConfig;
import com.liuruichao.listener.MySessionListener;
import com.liuruichao.service.IUserService;
import com.liuruichao.utils.lang.RandomUtils;
import com.liuruichao.utils.sms.JsonReqClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 短信服务
 *
 * @author liuruichao
 * Created on 2016-01-06 14:09
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {
    private final Logger logger = Logger.getLogger(SmsController.class);
    private final JsonReqClient jsonReqClient = new JsonReqClient();
    @Resource
    private IUserService userService;

    @RequestMapping("/userRegisterCode")
    @ResponseBody
    public Result<String> sendRegisterCode(@RequestParam String mobile, HttpServletRequest request) {
        Result<String> result = null;
        try {
            if (StringUtils.isBlank(mobile)) {
                return newFaildResult("手机号不得为空!");
            }
            if (userService.exists(mobile)) {
                return newFaildResult("该手机号已经注册!");
            }

            HttpSession session = request.getSession();

            DateTime curTime = new DateTime();
            // 每个手机号一分钟只能发送一条短信
            String sendTimeName = "register_" + mobile;
            String sendCodeName = "register_" + mobile + "_code";
            DateTime sendTime = (DateTime) session.getAttribute(sendTimeName);
            if (sendTime == null || sendTime.plusSeconds(60).isBefore(curTime)) {
                String randomCode = RandomUtils.randomCode(4, true);
                String sendResult = jsonReqClient.templateSMS(SmsConfig.ACCOUNT_SID,
                        SmsConfig.AUTH_TOKEN, SmsConfig.APP_ID, SmsConfig.TEMPLATE_ID, mobile, randomCode);
                logger.info(String.format("mobile : %s, sendRsult : %s.", mobile, sendResult));
                session.setAttribute(sendTimeName, curTime);
                session.setAttribute(sendCodeName, randomCode);
                result = newSuccessResult(ResultMessages.OPERATOR_SUCCESS, session.getId());
            } else {
                result = newFaildResult("请稍等一分钟再发送~");
            }
        } catch (Exception e) {
            logger.error("SmsController.sendRegisterCode()", e);
            result = newFaildResult(ResultMessages.SYSTEM_ERROR);
        }
        return result;
    }
}
