package com.eki.backend.bo;

import lombok.Data;

@Data
public class UserLoginResultBo {
    String userName;
    String role;
    String token;
}
