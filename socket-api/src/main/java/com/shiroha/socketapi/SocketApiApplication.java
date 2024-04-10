package com.shiroha.socketapi;

import com.shiroha.commonutils.utils.HttpClientUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(HttpClientUtils.class)
public class SocketApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketApiApplication.class, args);
    }

}
