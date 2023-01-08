package com.yuan.order.post.service;

import com.yuan.order.post.error.BusinessException;
import com.yuan.order.post.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);

    void userRegister(UserModel userModel) throws BusinessException;
    UserModel validUserLogin(String phone, String encryptPassword) throws BusinessException;
}
