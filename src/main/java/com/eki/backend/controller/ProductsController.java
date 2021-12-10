package com.eki.backend.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.eki.backend.bo.ImgResultBo;
import com.eki.backend.bo.ProductBo;
import com.eki.backend.bo.ProductBuyBo;
import com.eki.backend.bo.ProductBuyListBo;
import com.eki.backend.entity.SellState;
import com.eki.backend.service.IProductsService;
import com.eki.backend.vo.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/api/product")
public class ProductsController {

    @Autowired
    IProductsService productsService;

    //@SaCheckRole(value = {"admin, user"},mode = SaMode.OR)

    @GetMapping("/all")
    Result<List<ProductBo>> getProducts(){
        return productsService.getProducts();
    }

    @SaCheckRole("admin")
    @PostMapping("/add")
    Result<Integer> addProduct(@RequestBody ProductBo productBo){
        return productsService.addProduct(productBo);
    }

    @SaCheckRole("admin")
    @PostMapping("/update")
    Result<Integer> updateProduct(@RequestBody ProductBo productBo){
        return productsService.updateProduct(productBo);
    }

    @SaCheckRole("admin")
    @PostMapping("/delete")
    Result<Integer> deleteProduct(@RequestBody ProductBo productBo){
        return productsService.deleteProduct(productBo);
    }

    @GetMapping("/types")
    Result<List<String>> getAllTypes(){
        return productsService.getAllTypes();
    }


    @GetMapping("/byType")
    Result<List<ProductBo>> getProductsByType(@RequestParam("type") String type){
        return productsService.getProductsByType(type);
    }


    @GetMapping("/getProductById")
    Result<ProductBo> getProDuctById(@RequestParam("id") Integer id){
        return productsService.getProductByid(id);
    }

    @SaCheckRole("admin")
    @PostMapping("/uploadImg")
    Result<ImgResultBo> uploadImg(@RequestBody MultipartFile file){
        return productsService.upLoadImg(file);
    }


    @PostMapping("/buys")
    Result<String> buyProducts(@RequestBody ProductBuyListBo buylist){
        List<ProductBuyBo> list = new Gson().fromJson(buylist.getBuyList(),new TypeToken<List<ProductBuyBo>>(){}.getType());
        return productsService.buyProducts(list);
    }

    @PostMapping("/buy")
    Result<String> buyProduct(@RequestBody ProductBuyBo productBuyBo){
        return productsService.buyProduct(productBuyBo);
    }

    @SaCheckRole("admin")
    @GetMapping("/sellStates")
    Result<List<SellState>> getSellStates(){
        return productsService.getAllSellState();
    }
}

