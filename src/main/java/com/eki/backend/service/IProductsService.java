package com.eki.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eki.backend.bo.ImgResultBo;
import com.eki.backend.bo.ProductBo;
import com.eki.backend.bo.ProductBuyBo;
import com.eki.backend.entity.Products;
import com.eki.backend.entity.SellState;
import com.eki.backend.vo.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
public interface IProductsService extends IService<Products> {

    Result<List<ProductBo>> getProducts();

    Result<Integer> addProduct(ProductBo productBo);


    Result<Integer> updateProduct(ProductBo productBo);


    Result<Integer> deleteProduct(ProductBo productBo);


    Result<List<String>> getAllTypes();


    Result<List<ProductBo>> getProductsByType(String type);

    Result<ProductBo> getProductByid(Integer id);

    Result<ImgResultBo> upLoadImg(MultipartFile multipartFile);

    Result<List<SellState>> getAllSellState();

    Result<String> buyProducts(@RequestBody List<ProductBuyBo> productBuyBoList);

    Result<String> buyProduct(@RequestBody ProductBuyBo productBuyBo);
}
