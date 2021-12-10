package com.eki.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eki.backend.bo.UserViewBo;
import com.eki.backend.entity.User;
import com.eki.backend.entity.UserView;
import com.eki.backend.mapper.UserMapper;
import com.eki.backend.mapper.UserViewMapper;
import com.eki.backend.service.IUserViewService;
import com.eki.backend.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
@Service
@RequiredArgsConstructor
public class UserViewServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements IUserViewService {

    @NonNull
    UserViewMapper userViewMapper;

    @NonNull UserMapper userMapper;

    @Override
    public Result<List<UserView>> getUserView() {
        List<UserView> userViewList = userViewMapper.selectList(new QueryWrapper<UserView>());
        return new Result<>(userViewList);
    }

    @Override
    @Transactional
    public Result<String> addUserView(UserViewBo userViewBo) {
        UserView userView = new UserView();
        int uid =  userMapper.selectOne(new QueryWrapper<User>().eq("user_name",userViewBo.getUserName())).getUserId();
        userView.setUserId(uid);
        userView.setViewTime(userViewBo.getViewTime());
        userView.setProductId(userViewBo.getProductId());
        userView.setProductName(userViewBo.getProductName());
        userViewMapper.insert(userView);
        return new Result<>("添加用户查看日志成功");
    }
}
