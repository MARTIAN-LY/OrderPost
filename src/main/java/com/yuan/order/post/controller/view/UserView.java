package com.yuan.order.post.controller.view;

import com.yuan.order.post.service.model.UserModel;
import org.springframework.beans.BeanUtils;

public class UserView {
    private String phone;
    private String alias;

    public static UserView viewFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(userModel,userView);
        return userView;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
