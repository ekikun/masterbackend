package com.eki.backend.bo;

import lombok.Data;

@Data
public class ProductBo {
    Integer productId;
    String productName;
    String productType;
    Integer productCount;
    Double productPrice;
    String productDescription;
    String productImurl;
}
