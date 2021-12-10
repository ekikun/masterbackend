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
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("user_view")
@ApiModel(value = "UserView对象", description = "")
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer productId;

    private String productName;

    private String viewTime;


}
