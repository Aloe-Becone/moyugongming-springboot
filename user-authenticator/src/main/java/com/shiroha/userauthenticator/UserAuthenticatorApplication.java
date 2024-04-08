package com.shiroha.userauthenticator;

import com.shiroha.commonutils.bean.RedisConfiguration;
import com.shiroha.commonutils.utils.RedisCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.shiroha.userauthenticator.mapper")
@Import({RedisCache.class, RedisConfiguration.class})
public class UserAuthenticatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAuthenticatorApplication.class, args);
    }

}
