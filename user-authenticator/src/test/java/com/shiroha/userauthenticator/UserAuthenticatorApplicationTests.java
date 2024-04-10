package com.shiroha.userauthenticator;

import com.shiroha.commonutils.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@Slf4j
@Import(HttpClientUtils.class)
@ComponentScan(basePackages = "com.shiroha.commonutils.utils")
class UserAuthenticatorApplicationTests {

    @Autowired
    HttpClientUtils httpClientUtils;

    @Test
    void contextLoads() throws URISyntaxException {
        UserDetails user = httpClientUtils.getUserDetailsByPhoneNumber("19831902936");
        log.info(user.getUsername());
    }

    @Test
    void checkToken() throws UnsupportedEncodingException, URISyntaxException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjE5ODMxOTAyOTM2IiwianRpIjoiNjU2YTE0NDQtZWRlNS00ODE2LThjZDAtM2YzYWVmZGE5N2QyIiwiZXhwIjoxNzE1MTg3NTczLCJpYXQiOjE3MTI1OTU1NzMsInN1YiI6IlBlcmlwaGVyYWxzIiwiaXNzIjoiUmVtIn0.hdPeb7F2DxnyObHhOphhOvvPrEi6KNccKctK2HSXSII";
        CompletableFuture<Map<String, String>> future = httpClientUtils.checkAndRefreshToken(token);
        Map<String, String> result = future.join();
        if (result != null) {
            String phoneNumber = result.get("phoneNumber");
            try {
                UserDetails user = httpClientUtils.getUserDetailsByPhoneNumber(phoneNumber);
                log.info(user.toString());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Token check failed or refresh failed."); // 处理失败情况
        }
    }
}
