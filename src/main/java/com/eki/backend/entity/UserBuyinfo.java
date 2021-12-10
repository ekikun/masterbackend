package com.eki.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("user_buyinfo")
@ApiModel(value = "UserBuyinfo对象", description = "")
public class UserBuyinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String userEmail;

    private String userAddress;


}
