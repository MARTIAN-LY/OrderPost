package com.yuan.order.post.dao;

import com.yuan.order.post.entity.UserPassword;

public interface UserPasswordMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserPassword row);

    int insertSelective(UserPassword row);

    UserPassword selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserPassword row);

    int updateByPrimaryKey(UserPassword row);
}