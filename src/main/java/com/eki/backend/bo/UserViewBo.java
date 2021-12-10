package com.eki.backend.bo;

import lombok.Data;

@Data
public class UserViewBo {
    private String userName;

    private Integer productId;

    private String productName;

    private String viewTime;
}
