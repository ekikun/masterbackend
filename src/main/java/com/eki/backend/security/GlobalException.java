package com.eki.backend.security;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.eki.backend.constance.ResultConstance;
import com.eki.backend.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalException{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    // 全局异常拦截（拦截项目中的所有异常）
    @ResponseBody
    @ExceptionHandler
    public Result<String> handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 打印堆栈，以供调试
       logger.error("全局异常---------------");
        e.printStackTrace();

        // 不同异常返回不同状态码
        Result<String> result = null;
        if (e instanceof NotLoginException) {	// 如果是未登录异常
            NotLoginException ee = (NotLoginException) e;
            result = ResultConstance.UNALIGNED;
        }
        else if(e instanceof NotRoleException) {		// 如果是角色异常
            NotRoleException ee = (NotRoleException) e;
            result = ResultConstance.ROLE_DENIED;
        }
        else {
             result = ResultConstance.SERVER_ERROR;
        }

        // 返回给前端
        return result;
    }

}


