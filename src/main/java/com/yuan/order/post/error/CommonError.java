package com.yuan.order.post.error;

/**
 *@Author Martian
 *
 **/

public interface CommonError {
    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
