package com.eki.backend.service.impl;

import com.eki.backend.entity.Role;
import com.eki.backend.mapper.RoleMapper;
import com.eki.backend.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @NonNull
    RoleMapper roleMapper;


    @Override
    public String queryRole(String userName) {
        return null;
    }
}
