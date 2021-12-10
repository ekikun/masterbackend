package com.eki.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author eki
 * @since 2021-12-07
 */
@Getter
@Setter
@TableName("sell_state")
@ApiModel(value = "SellState对象", description = "")
public class SellState implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer productId;

    private String productName;

    private Integer productCount;

    private Integer sellCount;


}
