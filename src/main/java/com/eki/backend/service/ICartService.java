package com.eki.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eki.backend.bo.CartBo;
import com.eki.backend.bo.CartListBo;
import com.eki.backend.entity.Cart;
import com.eki.backend.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-08
 */
public interface ICartService extends IService<Cart> {
    Result<List<CartBo>> getAllCarts(String userName);

    Result<String> addCart(CartBo cartBo);

    Result<String> deleteCarts(CartListBo cartListBo);

    Result<String> deleteCart(CartBo cartBo);

    Result<String> updateCart(CartBo cartBo);

    Result<String> updateCarts(List<CartBo> cartList);
}
