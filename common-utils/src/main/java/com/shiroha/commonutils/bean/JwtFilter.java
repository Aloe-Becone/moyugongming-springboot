package com.shiroha.commonutils.bean;

import com.shiroha.commonutils.utils.HttpClientUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * @author Rem
 * jwt过滤器类，此过滤器类用在需要认证和鉴权的模块中
 * 区别于用户认证模块的jwt过滤器类，此类中通过调用用户认证模块公开的api来实现对token令牌的验证和获取用户权限信息
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final HttpClientUtils httpClientUtils;

    @Autowired
    public JwtFilter(HttpClientUtils httpClientUtils){
        this.httpClientUtils = httpClientUtils;
    }


    /**
     * <p>
     * TODO: 过滤链暂时未对用户权限进行验证
     * </p>
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        if (!jwtToken.isEmpty()) {
            // token不为空
            try {
                // 发起异步请求请求获取
                httpClientUtils.checkAndRefreshToken(jwtToken).thenAccept(result -> {
                    String phoneNumber = result.get("phoneNumber");
                    try {
                        UserDetails user = httpClientUtils.getUserDetailsByPhoneNumber(phoneNumber);
                        if(user != null){
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("token is null or empty or out of time, probably user is not log in !");
        }
        filterChain.doFilter(request, response);//继续过滤
    }
}
