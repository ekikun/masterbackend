package com.eki.backend.service.impl;

import com.eki.backend.entity.UserActivation;
import com.eki.backend.mapper.UserActivationMapper;
import com.eki.backend.service.IUserActivationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-05
 */
@Service
public class UserActivationServiceImpl extends ServiceImpl<UserActivationMapper, UserActivation> implements IUserActivationService {

}
