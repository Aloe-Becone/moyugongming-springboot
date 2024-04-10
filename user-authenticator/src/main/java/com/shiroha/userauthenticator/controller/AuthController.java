package com.shiroha.userauthenticator.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiroha.commonutils.bean.Result;
import com.shiroha.userauthenticator.service.Impl.UserServiceImpl;
import com.shiroha.userauthenticator.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证控制器，用于对其他模块公开用户认证的api
 * @author Rem
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserServiceImpl userService;


    /**
     * 对外提供检验token令牌的api
     * @param token 待认证的token
     * @return Result结果封装对象
     * <p>认证通过则data字段包含用户手机号</p>
     * <p>未通过认证则返回Result.error</p>
     */
    @GetMapping("/token/check")
    public Result checkToken(@RequestParam String token){
        if(StringUtil.isNullOrEmpty(token)) return Result.error("token为空！");

        // token检验成功后返回负载信息中的用户信息
        if(JwtUtils.checkToken(token)){
            Claims claims = JwtUtils.parsePayload(token);
            String phoneNumber = (String) claims.get("phoneNumber");
            log.info("token验证通过");
            return Result.ok("token检验成功").setData(phoneNumber);
        }else {
            // 检验失败
            return Result.error("token已过期");
        }
    }

    /**
     * 刷新token接口
     * @param token 待刷新的token
     * @return Result结果封装对象
     * <p>刷新成功则data字段为包含用户手机号和刷新的token的json序列字符串</p>
     */
    @GetMapping("/token/refresh")
    public Result refreshToken(@RequestParam String token){
        try {
            Claims claims = JwtUtils.parsePayload(token);
            String phoneNumber = (String) claims.get("phoneNumber");
            Map<String, String> result = new HashMap<>();

            // 生成新的token
            String newToken = JwtUtils.genAccessToken(phoneNumber);
            result.put("refresh_token", newToken);
            result.put("phoneNumber", phoneNumber);

            // 将map对象转为json字符串
            String jsonStr = JSONObject.toJSONString(result);
            log.info("token刷新成功"+jsonStr);
            return Result.ok("token刷新成功").setData(jsonStr);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 返回UserDetails对象
     * @param phoneNumber 用户的手机号
     * @return UserDetails
     */
    @GetMapping("/user/details")
    public UserDetails getUserByPhoneNumber(@RequestParam String phoneNumber){
        UserDetails userDetails = userService.loadUserByUsername(phoneNumber);
        if (userDetails != null){
            return userDetails;
        }
        else {
            log.error("userDetails is null!");
            return null;
        }
    }
}
