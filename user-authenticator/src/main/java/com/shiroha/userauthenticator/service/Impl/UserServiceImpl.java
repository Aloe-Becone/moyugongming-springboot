package com.shiroha.userauthenticator.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiroha.userauthenticator.domain.User;
import com.shiroha.userauthenticator.mapper.UserMapper;
import com.shiroha.userauthenticator.service.UserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * UserDetailsService中的重写方法
     */
    @Override
    @Cacheable(value = "userInfo", key = "#phoneNumber", unless = "#result == null")
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhoneNumber, phoneNumber);
        try {
            return userMapper.selectOne(wrapper);
        } catch (Exception e) {
            log.error("user is not find");
            return null;
        }
    }
    @Override
    public User findByPhoneNumber(String phoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phoneNumber", phoneNumber);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @CacheEvict(value = {"users", "userInfo"}, key = "#phoneNumber")
    public void insertOne(String userName, String password, String phoneNumber) {
        try {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhoneNumber, phoneNumber);
            if (userMapper.selectList(wrapper).isEmpty()) {
                User user = new User();
                user.setUserName(userName);
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password);
                userMapper.insert(user);
            } else {
                log.error("user already exists");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public User findById(Long id){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, String> getUserInfo(Long id) {
        User user = findById(id);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userName", user.getUserName());
        userInfo.put("id", user.getId().toString());
        return userInfo;
    }
}
