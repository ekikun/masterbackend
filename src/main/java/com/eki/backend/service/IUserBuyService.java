package com.eki.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eki.backend.entity.UserBuy;
import com.eki.backend.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-09
 */
public interface IUserBuyService extends IService<UserBuy> {

    Result<List<UserBuy>> getAllUserBuy();


}
