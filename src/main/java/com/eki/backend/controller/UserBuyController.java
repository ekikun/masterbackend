package com.eki.backend.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.eki.backend.entity.UserBuy;
import com.eki.backend.service.IUserBuyService;
import com.eki.backend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eki
 * @since 2021-12-09
 */
@RestController
@RequestMapping("/api")
public class UserBuyController {

    @Autowired
    IUserBuyService userBuyService;

    @SaCheckRole("admin")
    @GetMapping("/userbuy/all")
    Result<List<UserBuy>> getAllUserBuy(){
        return userBuyService.getAllUserBuy();
    }

}

