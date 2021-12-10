package com.eki.backend.controller;


import com.eki.backend.bo.CartBo;
import com.eki.backend.bo.CartListBo;
import com.eki.backend.service.ICartService;
import com.eki.backend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eki
 * @since 2021-12-08
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    ICartService cartService;


    @GetMapping("/all")
    Result<List<CartBo>> getAllCarts(@RequestParam String userName){
        return cartService.getAllCarts(userName);
    }


    @PostMapping("/addCart")
    Result<String> addCart(@RequestBody CartBo cartBo){
        return cartService.addCart(cartBo);
    }


    @PostMapping("/deleteCarts")
    Result<String> deleteCarts(@RequestBody CartListBo cartListBo){
        return cartService.deleteCarts(cartListBo);
    }


    @PostMapping("/updateCarts")
    Result<String> updateCarts(List<CartBo> cartList){
        return cartService.updateCarts(cartList);
    }

    @PostMapping("/updateCart")
    Result<String> updateCart(@RequestBody CartBo cartBo){
        return cartService.updateCart(cartBo);
    }

    @PostMapping("/deleteCart")
    Result<String> deleteCart(@RequestBody CartBo cartBo){
        return cartService.deleteCart(cartBo);
    }
}


