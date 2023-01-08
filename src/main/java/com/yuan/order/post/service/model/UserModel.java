package com.yuan.order.post.service.model;

import com.yuan.order.post.entity.UserInfo;
import com.yuan.order.post.entity.UserPassword;
import org.springframework.beans.BeanUtils;

public class UserModel {
    private Integer userId;
    private Short type;
    private String phone;
    private String schoolId;
    private String alias;
    private String encryptPassword;

    public static UserModel modelFromEntity(UserInfo userInfo, UserPassword userPassword) {
        UserModel model = new UserModel();
        BeanUtils.copyProperties(userInfo, model);
        model.setEncryptPassword(userPassword.getEncryptPassword());
        return model;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }
}
