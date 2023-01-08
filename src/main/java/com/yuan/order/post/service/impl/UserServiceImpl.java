package com.yuan.order.post.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yuan.order.post.dao.UserInfoMapper;
import com.yuan.order.post.dao.UserPasswordMapper;
import com.yuan.order.post.entity.UserInfo;
import com.yuan.order.post.entity.UserPassword;
import com.yuan.order.post.error.BusinessException;
import com.yuan.order.post.error.EmBusinessError;
import com.yuan.order.post.service.UserService;
import com.yuan.order.post.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserPasswordMapper userPasswordMapper;


    @Override
    public UserModel getUserById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        UserPassword userPassword = userPasswordMapper.selectByPrimaryKey(id);
        return UserModel.modelFromEntity(userInfo, userPassword);
    }

    @Override
    @Transactional
    public void userRegister(UserModel userModel) throws BusinessException {

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if (userModel.getType() == null
                || StringUtils.isEmpty(userModel.getPhone())
                || StringUtils.isEmpty(userModel.getAlias())
                || StringUtils.isEmpty(userModel.getSchoolId())
                || StringUtils.isEmpty(userModel.getEncryptPassword())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if (userInfoMapper.selectByPhone(userModel.getPhone()) != null) {
            throw new BusinessException(EmBusinessError.USER_ALREADY_EXISTS);
        }

        UserInfo userInfo = new UserInfo();
        UserPassword userPassword = new UserPassword();

        BeanUtils.copyProperties(userModel, userInfo);
        BeanUtils.copyProperties(userModel, userPassword);

        userInfoMapper.insertSelective(userInfo);
        userPasswordMapper.insertSelective(userPassword);
    }


    @Override
    public UserModel validUserLogin(String phone, String encryptPassword) throws BusinessException {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(encryptPassword)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserInfo userInfo = userInfoMapper.selectByPhone(phone);
        if (userInfo == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPassword userPassword = userPasswordMapper.selectByPrimaryKey(userInfo.getUserId());

        if (StringUtils.equals(userPassword.getEncryptPassword(), encryptPassword)) {
            return UserModel.modelFromEntity(userInfo, userPassword);
        }
        throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
    }

}
