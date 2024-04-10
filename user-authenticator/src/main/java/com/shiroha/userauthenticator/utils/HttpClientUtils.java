package com.shiroha.userauthenticator.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class HttpClientUtils {


    /**
     * 调用短信api接口发送短信验证码
     * @param phoneNumber 手机号
     * @param code 验证码
     */
    static public void SendSMSCode(String phoneNumber, int code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String appcode = "e5346af28c5e46e3bc25e77c1b1ed1bf";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "APPCODE " + appcode);

        // 设置请求体参数
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.add("content", String.format("code:%d", code));
        parameters.add("template_id", "CST_ptdie100");
        parameters.add("phone_number", phoneNumber);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.postForEntity(host + path, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            HttpStatusCode errorCode = e.getStatusCode();
            log.error("使用第三方api发送验证码出错：{},{}", errorCode, e.getMessage());
            throw e;
        }
    }
}