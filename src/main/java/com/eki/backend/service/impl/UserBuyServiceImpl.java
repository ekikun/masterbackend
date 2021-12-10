package com.eki.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eki.backend.entity.UserBuy;
import com.eki.backend.mapper.UserBuyMapper;
import com.eki.backend.service.IUserBuyService;
import com.eki.backend.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-09
 */
@Service
@RequiredArgsConstructor
public class UserBuyServiceImpl extends ServiceImpl<UserBuyMapper, UserBuy> implements IUserBuyService {

    @NonNull
    UserBuyMapper userBuyMapper;

    @Override
    public Result<List<UserBuy>> getAllUserBuy() {
        List<UserBuy> userBuys = userBuyMapper.selectList(new QueryWrapper<UserBuy>());
        return new Result<>(userBuys);
    }
}

