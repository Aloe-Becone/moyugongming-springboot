package com.shiroha.commonutils.VO;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User传输对象
 *
 * <p>用于在不同模块之前传输UserDetails接口的实例对象</p>
 *
 * @author Rem
 */
@Data
@Builder
public class UserVO implements UserDetails {

    private Long id;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 电话
    private String phoneNumber;
    // 权限
    private String role;

    @Override//用户所拥有的权限
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override//实现UserDetails的getPassword方法，返回实体类的password
    public String getPassword() {
        return password;
    }

    @Override//UserDetails中的方法
    public String getUsername() {
        // 使用手机号作为登录凭据
        return phoneNumber;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override//返回true，代表用户账号没过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override//返回true，代表用户账号没被锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override//返回true，代表用户密码没有过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override//返回true，代表用户账号还能够使用
    public boolean isEnabled() {
        return true;
    }
}
