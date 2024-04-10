package com.shiroha.commonutils.utils;

import com.alibaba.fastjson.JSONObject;
import com.shiroha.commonutils.VO.UserVO;
import com.shiroha.commonutils.bean.Result;
import com.shiroha.commonutils.bean.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 调用本地api的工具类，用于各模块统一调用用户认证的api
 *
 * @author Rem
 * @version 1.0
 */
@Slf4j
@Component
@Import(SysConfig.class)
public class HttpClientUtils {

    // 调用api主机地址
    private final String host = "localhost";

    // 端口号
    private final int port = 8080;

    private final RestTemplate restTemplate;

    @Autowired
    public HttpClientUtils(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * 检验并刷新token的异步方法，返回future对象
     * <p>
     *     TODO:刷新token后返回给客户端的逻辑需要优化
     * </p>
     * @param token 需要检验的token字符串
     * @return 当token检验成功时返回的map对象中包含解析出来的手机号，token检验失败时则包含了刷新后的token和手机号
     */
    public CompletableFuture<Map<String, String>> checkAndRefreshToken(String token) throws URISyntaxException, UnsupportedEncodingException {
        CompletableFuture<Map<String, String>> future = new CompletableFuture<>();
        String query = "token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

        try {
            // 发起检验token请求
            String checkPath = "/auth/token/check";
            URI checkUri = new URI("http", host + ":" + port, checkPath, query, null);
            ResponseEntity<Result> checkResponseEntity = restTemplate.getForEntity(checkUri, Result.class);
            Result checkResult = checkResponseEntity.getBody();

            if (checkResult != null && checkResult.getCode() == 200) {
                // token检验成功则将结果返回
                Map<String, String> map = new HashMap<>();
                map.put("phoneNumber", (String) checkResult.getData());
                future.complete(map);
            } else {
                // token检验失败，尝试刷新token
                String refreshPath = "/auth/token/refresh";
                URI refreshUri = new URI("http", host + ":" + port, refreshPath, query, null);
                ResponseEntity<Result> refreshResponseEntity = restTemplate.getForEntity(refreshUri, Result.class);
                Result refreshResult = refreshResponseEntity.getBody();

                if (refreshResult != null && refreshResult.getCode() == 200) {
                    JSONObject parseObject = JSONObject.parseObject((String) refreshResult.getData());
                    Map<String, String> map = new HashMap<>();
                    map.put("refresh_token", parseObject.getString("refresh_token"));
                    map.put("phoneNumber", parseObject.getString("phoneNumber"));
                    future.complete(map);
                } else {
                    future.complete(null); // 处理刷新 token 失败的情况
                }
            }
        } catch (Exception e) {
            future.completeExceptionally(e); // 处理异常情况
        }

        return future;
    }

    /**
     * 调用用户认证模块公开的api获取userDetails对象
     * @param phoneNumber 用户手机号
     * @return userDetails 调用失败时返回Null
     * @throws URISyntaxException uri异常
     */
    public UserDetails getUserDetailsByPhoneNumber(String phoneNumber) throws URISyntaxException {
        String query = "phoneNumber=" + URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
        String getUserPath = "/auth/user/details";
        URI userUri = new URI("http", host + ":" + port, getUserPath, query, null);
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(userUri, JSONObject.class);
        JSONObject responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            Long id = responseEntityBody.getLong("id");
            String userName = responseEntityBody.getString("userName");
            String password = responseEntityBody.getString("password");
            String phoneNumberX = responseEntityBody.getString("phoneNumber");
            String role = responseEntityBody.getString("role");
            return UserVO.builder()
                    .id(id)
                    .userName(userName)
                    .password(password)
                    .phoneNumber(phoneNumberX)
                    .role(role)
                    .build();
        }else return null;

    }
}
