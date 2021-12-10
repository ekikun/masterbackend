package com.eki.backend.bo;

import lombok.Data;

@Data
public class ProductBuyBo {
    private String userName;

    private Integer productId;

    private Integer buyCount;

    private String buyTime;
}
