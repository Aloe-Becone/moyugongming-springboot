package com.shiroha.userauthenticator.config.security;


import com.shiroha.commonutils.utils.JwtUtils;
import com.shiroha.userauthenticator.service.Impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
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

/**
 * jwt过滤器
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        if (!jwtToken.isEmpty() && JwtUtils.checkToken(jwtToken)) {
            try {//token可用
                Claims claims = JwtUtils.parsePayload(jwtToken);
                String phoneNumber = (String) claims.get("phoneNumber");
                UserDetails user = userService.loadUserByUsername(phoneNumber);
                if (user != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("token is null or empty or out of time, probably user is not log in !");
        }
        filterChain.doFilter(request, response);//继续过滤
    }
}
