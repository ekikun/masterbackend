package com.eki.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eki.backend.bo.UserViewBo;
import com.eki.backend.entity.UserView;
import com.eki.backend.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
public interface IUserViewService extends IService<UserView> {

    Result<List<UserView>> getUserView();


    Result<String> addUserView(UserViewBo userViewBo);
}
