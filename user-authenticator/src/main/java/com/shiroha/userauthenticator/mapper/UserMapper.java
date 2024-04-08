package com.shiroha.userauthenticator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiroha.userauthenticator.domain.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@CacheNamespace
public interface UserMapper extends BaseMapper<User> {
}
