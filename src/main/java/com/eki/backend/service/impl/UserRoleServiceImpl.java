package com.eki.backend.service.impl;

import com.eki.backend.entity.UserRole;
import com.eki.backend.mapper.UserRoleMapper;
import com.eki.backend.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
