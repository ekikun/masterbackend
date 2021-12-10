package com.eki.backend.bo;

import lombok.Data;

@Data
public class CartBo {
    private String userName;

    private Integer productId;

    private String productName;

    private Integer cartCount;

    private Double productPrice;

    private String productImurl;
}
