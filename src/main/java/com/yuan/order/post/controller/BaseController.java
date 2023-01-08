package com.yuan.order.post.controller;

import com.yuan.order.post.error.BusinessException;
import com.yuan.order.post.error.EmBusinessError;
import com.yuan.order.post.response.CommonReturnType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

/**
 *@Author Martian
 *
 **/

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    // 定义 ExceptionHandler 解决未被 Controller 层吸收的异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception exception) {

        HashMap<String, Object> responseData = new HashMap<>(2);

        if (exception instanceof BusinessException be) {
            responseData.put("errCode", be.getErrCode());
            responseData.put("errMsg", be.getErrMsg());
        } else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
