package com.eki.backend.service;

import com.eki.backend.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-03
 */
public interface IRoleService extends IService<Role> {
        String queryRole(String userName);
}
