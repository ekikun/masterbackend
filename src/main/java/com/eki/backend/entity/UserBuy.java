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
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("user_buy")
@ApiModel(value = "UserBuy对象", description = "")
public class UserBuy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer productId;

    private String productName;

    private Integer buyCount;

    private String buyTime;

}
