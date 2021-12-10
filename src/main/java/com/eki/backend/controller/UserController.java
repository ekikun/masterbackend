package com.eki.backend.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.eki.backend.bo.*;
import com.eki.backend.service.IUserService;
import com.eki.backend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eki
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/login")
    Result<UserLoginResultBo> login(@RequestBody UserLoginBo userLoginBo){
        return userService.login(userLoginBo);
    }

    @GetMapping("/logout")
    Result<String> logout(){
        return userService.logout();
    }

    @SaCheckRole("admin")
    @GetMapping("/admin")
    Result<List<AdminBo>> getAdminInfo(){
        return userService.getAdminInfo();
    }

    @PostMapping("/register")
    Result<UserRegisterResultBo> register(@RequestBody UserRegisterBo userRegisterBo){
        return userService.register(userRegisterBo);
    }

    @PostMapping("/userActivate")
    Result<String> userActivate(@RequestBody UserActivateBo userActivateBo){
        return userService.userActivate(userActivateBo);
    }

    @GetMapping("/userAddress")
    Result<UserBuyInfoBo> getUserAddress(@RequestParam String userName){
        return userService.getUserAddress(userName);
    }

    @PostMapping("/addUserAddress")
    Result<String> addUserAddress(@RequestBody UserBuyInfoBo userBuyInfoBo){
        return userService.addUserAddress(userBuyInfoBo);
    }
}

