package com.shiroha.userauthenticator.controller;

import com.alibaba.fastjson2.JSON;

import com.shiroha.commonutils.bean.Result;
import com.shiroha.commonutils.utils.JwtUtils;
import com.shiroha.commonutils.utils.RedisCache;
import com.shiroha.userauthenticator.domain.User;
import com.shiroha.userauthenticator.requests.AuthRequest;
import com.shiroha.userauthenticator.service.Impl.UserServiceImpl;
import com.shiroha.userauthenticator.utils.HttpClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final private AuthenticationManager authenticationManager;

    final private PasswordEncoder passwordEncoder;

    final private RedisCache redisCache;

    final private UserServiceImpl userService;

    // 生成随机用户名
    private static String genUsername() {

        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String USERNAME_PREFIX = "用户";
        int USERNAME_LENGTH = 8;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(USERNAME_LENGTH);
        for (int i = 0; i < USERNAME_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        sb.insert(0, USERNAME_PREFIX);

        return sb.toString();
    }

    @GetMapping("/sendSMS")
    public ResponseEntity<Result> sendSMS(@RequestParam String phoneNumber) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        int code = Integer.parseInt(str.toString());
        try {
            HttpClientUtils.SendSMSCode(phoneNumber, code);
            redisCache.setCacheObject(phoneNumber, code, 5, TimeUnit.MINUTES);
            return ResponseEntity.ok(Result.ok("验证码发送成功"));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("验证码发送失败，请联系客服反馈"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Result> Login(@RequestBody AuthRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String code = request.getCode();
        String password = request.getPassword();

        // 检查用户是否存在
        User user = userService.findByPhoneNumber(phoneNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.error("用户未注册"));
        }

        // 使用验证码登录，redis检验验证码正确则直接签发token
        if (code != null && password == null) {
            if (redisCache.getCacheObject(phoneNumber) != null && redisCache.getCacheObject(phoneNumber).equals(Integer.parseInt(code))) {
                String token = JwtUtils.genAccessToken(phoneNumber);
                redisCache.deleteObject(phoneNumber);
                return ResponseEntity.ok(Result.ok("登录成功").setData(token));
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error("验证码错误或已过期"));
        }

        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(phoneNumber, password);
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JwtUtils.genAccessToken(userDetails.getUsername());
            return ResponseEntity.ok(Result.ok("登录成功").setData(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.error("用户名或密码错误"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Result> Register(@RequestBody AuthRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String code = request.getCode();
        String password = request.getPassword();

        User user = userService.findByPhoneNumber(phoneNumber);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.error("该手机号已注册"));
        }

        if (redisCache.getCacheObject(phoneNumber) != null && redisCache.getCacheObject(phoneNumber).equals(Integer.parseInt(code))) {
            String username = genUsername();
            String encoded = passwordEncoder.encode(password);
            userService.insertOne(username, encoded, phoneNumber);
            String token = JwtUtils.genAccessToken(phoneNumber);
            redisCache.deleteObject(phoneNumber);
            return ResponseEntity.ok(Result.ok("注册成功").setData(token));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error("验证码错误或已过期"));
        }
    }


    @GetMapping("/getUserInfo")
    public ResponseEntity<String> getUserInfo(@RequestParam Long id) {
        try {
            Map<String, String> userInfo = userService.getUserInfo(id);
            String jsonStr = JSON.toJSONString(userInfo);
            return ResponseEntity.ok(jsonStr);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("请求失败，请稍后重试");
        }
    }
}
