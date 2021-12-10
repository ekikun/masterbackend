package com.eki.backend.constance;

import com.eki.backend.vo.Result;

public class ResultConstance {
    public final static Result ACCOUNT_ERROR = new Result<>(2001,"账号未注册");
    public final static Result PASSWORD_ERROR = new Result<>(2002,"登录密码错误");
    public final static Result ROLE_DENIED = new Result<>(2003, "没有访问权限");
    public final static Result UNACTIVATED = new Result<>(2004,"账号未激活");
    public final static Result UNALIGNED = new Result<>(2005,"请登录");
    public final static Result ACCOUNT_EXISTS = new Result<>(2006,"账号已存在");
    public final static Result SERVER_ERROR = new Result<>(2007, "服务器错误");
    public final static Result ACTIVATE_SUCCESS = new Result<>(1001, "账号成功激活");
    private ResultConstance(){

    }
}
