package com.shiroha.userauthenticator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiroha.userauthenticator.domain.User;

import java.util.Map;


public interface UserService extends IService<User> {
    User findByPhoneNumber(String phoneNumber);

    User findById(Long id);

    void insertOne(String userName, String password, String phoneNumber);

    Map<String, String> getUserInfo(Long id);
}
