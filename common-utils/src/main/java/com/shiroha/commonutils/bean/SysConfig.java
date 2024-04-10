package com.shiroha.commonutils.bean;

import com.shiroha.commonutils.utils.HttpClientUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 用到的公共bean
 */
@Component
public class SysConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public HttpClientUtils httpClientUtils(RestTemplate restTemplate){
        return new HttpClientUtils(restTemplate);
    }
}
