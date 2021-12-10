package com.eki.backend.service.impl;

import com.eki.backend.redis.RedisUtil;
import com.eki.backend.service.IMailService;
import com.eki.backend.service.IUserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserEmailServiceImpl implements IUserEmailService {

    @Autowired
    IMailService iMailService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void sendUserActivateEmail(String email, String userName, String backendUrl) {
        String url = backendUrl+"/userActivate?username="+userName;
        iMailService.sendSimpleMail(email, "KIKI商城确认邮件", "请点击以下链接确认注册: "+url);
    }

    @Override
    public void sendProductBuyEmail(String email, String content) {
        iMailService.sendSimpleMail(email,"KIKI商城确认发货邮件", content);
    }


}
