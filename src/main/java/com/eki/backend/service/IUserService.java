package com.eki.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eki.backend.bo.*;
import com.eki.backend.entity.User;
import com.eki.backend.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-03
 */
public interface IUserService extends IService<User> {

    Result<UserLoginResultBo> login(UserLoginBo userLoginBo);

    Result<UserRegisterResultBo> register(UserRegisterBo userRegisterBo);

    Result<String> userActivate(UserActivateBo userActivateBo);

    String getRole(String userName);

    Result<String> logout();

    Result<List<AdminBo>> getAdminInfo();

    Result<UserBuyInfoBo> getUserAddress(String userName);

    Result<String> addUserAddress(UserBuyInfoBo userBuyInfoBo);
}
