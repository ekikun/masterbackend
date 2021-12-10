package com.eki.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eki.backend.entity.Products;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
public interface ProductsMapper extends BaseMapper<Products> {
    List<String> getAllTypes();
}
