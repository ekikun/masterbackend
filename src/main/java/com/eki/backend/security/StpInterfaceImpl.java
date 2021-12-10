package com.eki.backend.security;

import cn.dev33.satoken.stp.StpInterface;
import com.eki.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {


    @Autowired
    IUserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
         System.out.println("查询角色: "+loginId);
         String role = userService.getRole(String.valueOf(loginId));
         List<String> roleList =  new ArrayList<String>();
         roleList.add(role);
         System.out.println(roleList);
         return roleList;
    }
}
