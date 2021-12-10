package com.eki.backend.controller;


import com.eki.backend.bo.UserViewBo;
import com.eki.backend.entity.UserView;
import com.eki.backend.service.IUserViewService;
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
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/api")
public class UserViewController {

    @Autowired
    IUserViewService userViewService;

    @GetMapping("/userview/all")
    Result<List<UserView>> getUserView(){
        return  userViewService.getUserView();
    }

    @PostMapping("/userview/add")
    Result<String> addUserView(@RequestBody UserViewBo userViewBo){
        return userViewService.addUserView(userViewBo);
    }
}

