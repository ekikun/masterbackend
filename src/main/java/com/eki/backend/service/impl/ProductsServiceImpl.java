package com.eki.backend.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eki.backend.bo.*;
import com.eki.backend.config.AliyunOssConfig;
import com.eki.backend.entity.*;
import com.eki.backend.mapper.*;
import com.eki.backend.service.IProductsService;
import com.eki.backend.service.IUserEmailService;
import com.eki.backend.vo.Result;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eki
 * @since 2021-12-06
 */
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @NonNull
    ProductsMapper productsMapper;

    @NonNull
    SellStateMapper sellStateMapper;

    @NonNull UserMapper userMapper;

    @NonNull UserBuyMapper userBuyMapper;

    @NonNull UserBuyinfoMapper userBuyinfoMapper;


    @Autowired
    OSS ossClient;

    @Autowired
    AliyunOssConfig aliyunOssConfig;

    @Autowired
    IUserEmailService userEmailService;

    @Override
    public Result<List<ProductBo>> getProducts() {
        List<Products> productsList = productsMapper.selectList(new QueryWrapper<>());
        List<ProductBo> productBoList = new ArrayList<>();
        for(Products products:productsList){
            ProductBo productBo = products2ProductBo(products);
            productBoList.add(productBo);
        }
        return new Result<>(productBoList);
    }

    @Override
    @Transactional
    public Result<Integer> addProduct(ProductBo productBo) {
        Products products = productBo2Products(productBo);
        System.out.println("productName: "+products.getProductName());
        productsMapper.insert(products);
        int pid = products.getProductId();
        SellState sellState = new SellState();
        sellState.setProductId(pid);
        sellState.setProductCount(productBo.getProductCount());
        sellState.setProductName(productBo.getProductName());
        sellState.setSellCount(0);
        sellStateMapper.insert(sellState);
        return new Result<>(pid);
    }

    @Override
    @Transactional
    public Result<Integer> updateProduct(ProductBo productBo) {
        Products products = productBo2Products(productBo);
        productsMapper.update(products,new UpdateWrapper<Products>().eq(("product_id"),products.getProductId()));
        return new Result<>(productBo.getProductId());
    }

    @Override
    @Transactional
    public Result<Integer> deleteProduct(ProductBo productBo) {
        System.out.println("product2Delete Id: "+productBo.getProductId());
        sellStateMapper.delete(new QueryWrapper<SellState>().eq("product_id",productBo.getProductId()));
        productsMapper.delete(new QueryWrapper<Products>().eq(("product_id"),productBo.getProductId()));
        return new Result<>(productBo.getProductId());
    }

    @Override
    public Result<List<String>> getAllTypes(){
        List<String> typeList = productsMapper.getAllTypes();
        return new Result<>(typeList);
    }

    @Override
    public Result<List<ProductBo>> getProductsByType(String type) {
        List<Products> productsList = productsMapper.selectList((new QueryWrapper<Products>().eq("product_type",type)));
        List<ProductBo> productBoList = new ArrayList<>();
        for(Products products:productsList){
            ProductBo productBo = products2ProductBo(products);
            productBoList.add(productBo);
        }
        return new Result<>(productBoList);
    }

    @Override
    public Result<ProductBo> getProductByid(Integer id) {
        Products products = productsMapper.selectOne(new QueryWrapper<Products>().eq("product_id",id));
        ProductBo productBo = products2ProductBo(products);
        return new Result<>(productBo);
    }

    @Override
    public Result<ImgResultBo> upLoadImg(MultipartFile uploadFile) {
        // 获取oss的Bucket名称
        String bucketName = aliyunOssConfig.getBucketName();
        // 获取oss的地域节点
        String endpoint = aliyunOssConfig.getEndPoint();
        // 获取oss的AccessKeySecret
        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
        // 获取oss的AccessKeyId
        String accessKeyId = aliyunOssConfig.getAccessKeyId();
        // 获取oss目标文件夹
        String filehost = aliyunOssConfig.getFileHost();
        // 返回图片上传后返回的url
        String returnImgUrl = "";

        // 获取文件原名称
        String originalFilename = uploadFile.getOriginalFilename();
        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名称
        String newFileName = UUID.randomUUID().toString() + fileType;
        // 构建日期路径, 例如：OSS目标文件夹/2020/10/31/文件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // 文件上传的路径地址
        String uploadImgeUrl = filehost + "/" + filePath + "/" + newFileName;

        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        ossClient.putObject(bucketName, uploadImgeUrl, inputStream, meta);

        returnImgUrl = "http://" + bucketName + "." + endpoint + "/" + uploadImgeUrl;

        ImgResultBo imgResultBo = new ImgResultBo();
        imgResultBo.setImgName(newFileName);
        imgResultBo.setImgUrl(returnImgUrl);
        return new Result<>(imgResultBo);
    }

    @Override
    public Result<List<SellState>> getAllSellState() {
        List<SellState> sellStates = sellStateMapper.selectList(new QueryWrapper<>());
        return new Result<>(sellStates);
    }

    @Override
    @Transactional
    public Result<String> buyProducts(List<ProductBuyBo> productBuyBoList) {
        UserBuyinfo userBuyinfo = userBuyinfoMapper.selectOne(new QueryWrapper<UserBuyinfo>().eq("user_name",productBuyBoList.get(0).getUserName()));
        List<String> productNames = new ArrayList<>();
        for(ProductBuyBo productBuyBo:productBuyBoList){
            handleBuy(productBuyBo,productNames);
        }
        String email = userBuyinfo.getUserEmail();
        String address = userBuyinfo.getUserAddress();
        sendEmail(email,productNames,address);
        return new Result<String>("购买成功, 库存不足的未购买,请查看发货邮件");
    }

    @Override
    @Transactional
    public Result<String> buyProduct(ProductBuyBo productBuyBo) {
        List<String> productNames = new ArrayList<>();
        handleBuy(productBuyBo,productNames);
        UserBuyinfo userBuyinfo = userBuyinfoMapper.selectOne(new QueryWrapper<UserBuyinfo>().eq("user_name",productBuyBo.getUserName()));
        String email = userBuyinfo.getUserEmail();
        String address = userBuyinfo.getUserAddress();
        sendEmail(email,productNames,address);
       return new Result<String>("购买成功, 库存不足的未购买，请查看发货邮件");
    }



    private ProductBo products2ProductBo(Products products){
        ProductBo productBo = new ProductBo();
        productBo.setProductId(products.getProductId());
        productBo.setProductCount(products.getProductCount());
        productBo.setProductPrice(products.getProductPrice());
        productBo.setProductName(products.getProductName());
        productBo.setProductImurl(products.getProductImurl());
        productBo.setProductType(products.getProductType());
        productBo.setProductDescription(products.getProductDescription());
        return productBo;
    }

    private Products productBo2Products(ProductBo productBo){
        Products products = new Products();
        products.setProductId(productBo.getProductId());
        products.setProductCount(productBo.getProductCount());
        products.setProductPrice(productBo.getProductPrice());
        products.setProductName(productBo.getProductName());
        products.setProductImurl(productBo.getProductImurl());
        products.setProductType(productBo.getProductType());
        products.setProductDescription(productBo.getProductDescription());
        return products;
    }

    @Transactional
    void handleBuy(ProductBuyBo productBuyBo, List<String> productNames){
        int pid = productBuyBo.getProductId();
        int buyCount = productBuyBo.getBuyCount();
        Products products = productsMapper.selectOne(new QueryWrapper<Products>().eq("product_id",pid));
        int productCount = products.getProductCount();
        if(productCount-buyCount>=0){
            productCount -= buyCount;
            productNames.add(products.getProductName());
            products.setProductCount(productCount);
            productsMapper.update(products, new UpdateWrapper<Products>().eq("product_id",pid));
            SellState sellState = sellStateMapper.selectOne(new QueryWrapper<SellState>().eq("product_id",pid));
            sellState.setProductCount(productCount);
            sellState.setSellCount(buyCount+sellState.getSellCount());
            sellStateMapper.update(sellState,new UpdateWrapper<SellState>().eq("product_id",pid));
        }
        handleBuyTrace(productBuyBo,products.getProductName());
    }

    private void sendEmail(String email, List<String> productNames, String address){
        StringBuilder sb = new StringBuilder();
        sb.append("您购买了以下商品: ");
        for(String name:productNames){
            sb.append(name+", ");
        }
        sb.append("已经发货到您的地址: "+address);
        userEmailService.sendProductBuyEmail(email,sb.toString());
    }

    @Transactional
    void handleBuyTrace(ProductBuyBo productBuyBo, String productName){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",productBuyBo.getUserName()));
        UserBuy userBuy = new UserBuy();
        userBuy.setUserId(user.getUserId());
        userBuy.setProductId(productBuyBo.getProductId());
        userBuy.setProductName(productName);
        userBuy.setBuyCount(productBuyBo.getBuyCount());
        userBuy.setBuyTime(productBuyBo.getBuyTime());
        userBuyMapper.insert(userBuy);
    }
}
