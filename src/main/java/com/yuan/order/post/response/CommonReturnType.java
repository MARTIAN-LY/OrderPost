package com.yuan.order.post.response;

/**
 *@Author Martian
 *
 **/

public class CommonReturnType {

    // 表明对应请求的返回结果是 success 还是 fail
    private String status;

    // status = success , 则 data 内返回前端需要的 json 数据
    // status = fail , 则 data 内使用通用的错误码格式
    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }
    public static CommonReturnType create(Object result, String status) {
        return new CommonReturnType(status, result);
    }

    private CommonReturnType() {
    }

    private CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
