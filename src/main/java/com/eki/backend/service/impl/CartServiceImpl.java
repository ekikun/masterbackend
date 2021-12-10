package com.eki.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eki.backend.bo.CartBo;
import com.eki.backend.bo.CartListBo;
import com.eki.backend.bo.UserInfoBo;
import com.eki.backend.entity.Cart;
import com.eki.backend.entity.User;
import com.eki.backend.mapper.CartMapper;
import com.eki.backend.mapper.UserMapper;
import com.eki.backend.redis.RedisUtil;
import com.eki.backend.service.ICartService;
import com.eki.backend.service.IUserService;
import com.eki.backend.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-08
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @NonNull
    CartMapper cartMapper;

    @NonNull
    UserMapper userMapper;

    @NonNull
    RedisUtil redisUtil;

    @Autowired
    IUserService userService;

    @Override
    public Result<List<CartBo>> getAllCarts(String userName) {
        int uid = getUid(userName);
        List<Cart> cartList = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id",uid));
        List<CartBo> cartBoList = new ArrayList<>();
        for(Cart cart:cartList){
            cartBoList.add(cart2CartBo(cart));
        }
        return new Result<>(cartBoList);
    }

    @Override
    @Transactional
    public Result<String> addCart(CartBo cartBo) {
        Cart cart = cartBo2Cart(cartBo);
        Cart ecart  = cartMapper.selectOne(new QueryWrapper<Cart>().eq("product_id",cart.getProductId()).eq("user_id",cart.getUserId()));
        if(ecart!=null){
            ecart.setCartCount(ecart.getCartCount()+cart.getCartCount());
            cartMapper.update(ecart,new UpdateWrapper<Cart>().eq("product_id",cart.getProductId()).eq("user_id",cart.getUserId()));
        }else{
            cartMapper.insert(cart);
        }
        return new Result<>("添加到购物车表成功");
    }

    @Override
    @Transactional
    public Result<String> deleteCarts(CartListBo cartListBo) {
        List<CartBo> cartBoList = cartListBo.getCartBoList();
        for(CartBo cartBo:cartBoList){
            Cart cart = cartBo2Cart(cartBo);
            cartMapper.delete(new QueryWrapper<Cart>().eq("product_id",cart.getProductId()));
        }
        return new Result<>("从购物车中移除成功");
    }

    @Override
    public Result<String> deleteCart(CartBo cartBo) {
        Cart cart = cartBo2Cart(cartBo);
        cartMapper.delete(new QueryWrapper<Cart>().eq("product_id",cart.getProductId()));
        return new Result<>("从购物车中移除成功");
    }

    @Override
    public Result<String> updateCart(CartBo cartBo) {
        Cart cart = cartBo2Cart(cartBo);
        cartMapper.update(cart, new UpdateWrapper<Cart>().eq("product_id",cart.getProductId()));
        return new Result<>("更新到购物车表成功");
    }

    @Override
    @Transactional
    public Result<String> updateCarts(List<CartBo> cartList) {
        for (CartBo cartBo:cartList){
            Cart cart = cartBo2Cart(cartBo);
            cartMapper.update(cart, new UpdateWrapper<Cart>().eq("product_id",cart.getCartCount()));
        }
        return new Result<>("更新到购物车表成功");
    }

    private int getUid(String userName){
        int uid = -1;
        if (redisUtil.hasKey(userName)){
            uid = ((UserInfoBo)redisUtil.get(userName)).getUserId();
        }else{
            uid = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",userName)).getUserId();
        }
        return uid;
    }

    private String getUserName(int uid){
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id",uid)).getUserName();
    }

    private Cart cartBo2Cart(CartBo cartBo){
        int uid = getUid(cartBo.getUserName());
        Cart cart = new Cart();
        cart.setCartCount(cartBo.getCartCount());
        cart.setProductId(cartBo.getProductId());
        cart.setProductImurl(cartBo.getProductImurl());
        cart.setProductPrice(cartBo.getProductPrice());
        cart.setUserId(uid);
        cart.setProductName(cartBo.getProductName());
        return cart;
    }

    private CartBo cart2CartBo(Cart cart){
        String userName = getUserName(cart.getUserId());
        CartBo cartBo = new CartBo();
        cartBo.setCartCount(cart.getCartCount());
        cartBo.setProductId(cart.getProductId());
        cartBo.setProductImurl(cart.getProductImurl());
        cartBo.setProductPrice(cart.getProductPrice());
        cartBo.setUserName(userName);
        cartBo.setProductName(cart.getProductName());
        return cartBo;
    }
}

