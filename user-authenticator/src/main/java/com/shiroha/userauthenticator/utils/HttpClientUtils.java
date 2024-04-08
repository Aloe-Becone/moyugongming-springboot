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

    static public ResponseEntity<String> invokeOpenAPI(String prompt) throws URISyntaxException {
        String url = "https://api.openai.com/v1/images/generations";
        String secretKey = "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";

        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + secretKey);


        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.set("prompt", prompt);
        parameters.set("n", 1);
        parameters.set("size", "256x256");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info(response.getBody());
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("error:" + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            HttpStatusCode errorCode = e.getStatusCode();
            return ResponseEntity.badRequest().body("fail:"+ errorCode);
        }
    }
}