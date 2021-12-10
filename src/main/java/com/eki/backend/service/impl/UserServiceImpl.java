package com.eki.backend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eki.backend.bo.*;
import com.eki.backend.constance.ResultConstance;
import com.eki.backend.entity.*;
import com.eki.backend.mapper.*;
import com.eki.backend.redis.RedisUtil;
import com.eki.backend.service.IUserService;
import com.eki.backend.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-03
 */


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @NonNull
    UserMapper userMapper;
    @NonNull
    UserRoleMapper userRoleMapper;
    @NonNull
    RoleMapper roleMapper;
    @NonNull
    UserActivationMapper userActivationMapper;
    @NonNull UserBuyinfoMapper userBuyinfoMapper;

    @Autowired
    UserEmailServiceImpl userEmailService;
    @NonNull
    RedisUtil redisUtil;

    @Value("${backend.url}")
    String backendUrl;

    final static int ACTIVATED = 1;

    final static int UNACTIVATED = 0;

    final static int ROLE_ID_USER = 1;

    final static int ROLE_ID_UNACTIVATED_USER = 3;

    @Override
    @Transactional
    public Result<UserLoginResultBo> login(UserLoginBo userLoginBo){
            String loginUserName = userLoginBo.getUserName();
            UserInfoBo userInfoBo = null;
            int uid = -1;
            if(redisUtil.hasKey(loginUserName)){
                userInfoBo = (UserInfoBo) redisUtil.get(loginUserName);
                uid = userInfoBo.getUserId();
            }else{
                User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",loginUserName));
                if(user==null){
                    return ResultConstance.ACCOUNT_ERROR;
                }
                uid = user.getUserId();
            }
            UserRole userRole  = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", uid));
            String passWord = userRole.getPassword();
            if(!passWord.equals(userLoginBo.getPassWord())) {
                return ResultConstance.PASSWORD_ERROR;
            }
            StpUtil.login(loginUserName);
            String token = StpUtil.getTokenValue();
            UserLoginResultBo userLoginResultBo = new UserLoginResultBo();
            userLoginResultBo.setUserName(loginUserName);
            userLoginResultBo.setToken(token);
            String role = getRole(loginUserName);
            userLoginResultBo.setRole(role);
            System.out.println(userLoginResultBo.getToken()+" "+userLoginResultBo.getRole());
            return new Result<>(userLoginResultBo);
    }

    @Override
    @Transactional
    public Result<UserRegisterResultBo> register(UserRegisterBo userRegisterBo) {
        String registerUserName = userRegisterBo.getUsername();
        if(redisUtil.hasKey(registerUserName)
                ||userMapper.selectOne(new QueryWrapper<User>().eq("user_name",registerUserName))!=null){
            return ResultConstance.ACCOUNT_EXISTS;
        }else {
            User user = new User();
            user.setUserName(userRegisterBo.getUsername());
            user.setUserEmail(userRegisterBo.getEmail());
            userMapper.insert(user);
            int uid = user.getUserId();
            UserRole userRole = new UserRole();
            userRole.setRoleId(ROLE_ID_UNACTIVATED_USER);
            userRole.setUserId(uid);
            userRole.setPassword(userRegisterBo.getPassword());
            userRoleMapper.insert(userRole);
            UserActivation userActivation = new UserActivation();
            userActivation.setUserId(uid);
            userActivation.setActivated(UNACTIVATED);
            userActivationMapper.insert(userActivation);
            userEmailService.sendUserActivateEmail(user.getUserEmail(),user.getUserName(),backendUrl);
        }
        UserRegisterResultBo userRegisterResultBo = new UserRegisterResultBo();
        userRegisterResultBo.setUsername(registerUserName);
        userRegisterResultBo.setHit("注册成功，请前往邮箱登录");
        return new Result<>(userRegisterResultBo);
    }

    @Override
    @Transactional
    public Result<String> userActivate(UserActivateBo userActivateBo) {
        String userName = userActivateBo.getUsername();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq(("user_name"),userName));
        int uid = user.getUserId();
        UserActivation userActivation = new UserActivation();
        userActivation.setUserId(uid);
        userActivation.setActivated(ACTIVATED);
        userActivationMapper.update(userActivation,new UpdateWrapper<UserActivation>().eq(("user_id"),uid));
        UserRole userRole = new UserRole();
        userRole.setUserId(uid);
        userRole.setRoleId(ROLE_ID_USER);
        userRoleMapper.update(userRole,new UpdateWrapper<UserRole>().eq("user_id",uid));
        return ResultConstance.ACTIVATE_SUCCESS;
    }

    @Override
    public String getRole(String loginUserName){
        UserInfoBo userInfoBo = new UserInfoBo();
        if(!redisUtil.hasKey(loginUserName)){
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",loginUserName));
            int userId = user.getUserId();
            UserRole userRole  = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId));
            int roleId = userRole.getRoleId();
            String role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_id",roleId)).getRole();
            userInfoBo.setUserId(userId);
            userInfoBo.setUserName(loginUserName);
            userInfoBo.setRole(role);
            redisUtil.set(loginUserName, userInfoBo);
        }else{
            userInfoBo = (UserInfoBo) redisUtil.get(loginUserName);
        }
        return userInfoBo.getRole();
    }

    @Override
    public Result<String> logout() {
        StpUtil.logout();
        return new Result<>("退出登录成功");
    }

    @Override
    public Result<List<AdminBo>> getAdminInfo() {
        List<User> list = userMapper.selectList(new QueryWrapper<>());
        List<AdminBo> resultList = new ArrayList<>();
        for(User user:list){
            AdminBo adminBo = new AdminBo();
            BeanUtils.copyProperties(user,adminBo);
            resultList.add(adminBo);
        }
        return new Result<>(resultList);
    }

    @Override
    public Result<UserBuyInfoBo> getUserAddress(String userName) {
        UserBuyInfoBo userBuyInfoBo = new UserBuyInfoBo();
        UserBuyinfo userBuyinfo = userBuyinfoMapper.selectOne(new QueryWrapper<UserBuyinfo>().eq("user_name",userName));
        if(userBuyinfo==null){
            userBuyinfo = new UserBuyinfo();
        }
        userBuyInfoBo.setUserName(userName);
        userBuyInfoBo.setUserEmail(userBuyinfo.getUserEmail());
        userBuyInfoBo.setUserAddress(userBuyinfo.getUserAddress());
        return new Result<>(userBuyInfoBo);
    }

    @Override
    @Transactional
    public Result<String> addUserAddress(UserBuyInfoBo userBuyInfoBo) {
        int uid = -1;
        if(redisUtil.hasKey(userBuyInfoBo.getUserName())){
            UserInfoBo userInfoBo = (UserInfoBo) redisUtil.get(userBuyInfoBo.getUserName());
            uid = userInfoBo.getUserId();
        }else{
            uid = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userBuyInfoBo.getUserName())).getUserId();
        }
        UserBuyinfo userBuyinfo = new UserBuyinfo();
        userBuyinfo.setUserId(uid);
        userBuyinfo.setUserName(userBuyInfoBo.getUserName());
        userBuyinfo.setUserEmail(userBuyInfoBo.getUserEmail());
        userBuyinfo.setUserAddress(userBuyInfoBo.getUserAddress());
        userBuyinfoMapper.insert(userBuyinfo);
        return new Result<>("添加用户地址和通讯方式成功");
    }

}
