package com.eki.backend.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author eki
 * @since 2021-12-08
 */
@Getter
@Setter
@ApiModel(value = "Cart对象", description = "")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer productId;

    private String productName;

    private Integer cartCount;

    private Double productPrice;

    private String productImurl;


}
