package com.shiroha.userauthenticator.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Slf4j
@Component
public class JwtUtils {


    // 有效期为一个月
    public static final int ACCESS_EXPIRE = 60 * 60 * 24 * 30;
    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private final static String SECRET = "3F4428472B4B6250655368566D5971337336763979244226452948404D635166";
    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "Rem";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "Peripherals";

    /*
    这些是一组预定义的声明，它们 不是强制性的，而是推荐的 ，以提供一组有用的、可互操作的声明 。
    iss: jwt签发者
    sub: jwt所面向的用户
    aud: 接收jwt的一方
    exp: jwt的过期时间，这个过期时间必须要大于签发时间
    nbf: 定义在什么时间之前，该jwt都是不可用的.
    iat: jwt的签发时间
    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */
    public static String genAccessToken(String phoneNumber) {
        // 令牌id
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息payload
                .claim("phoneNumber", phoneNumber)
                // 令牌ID
                .id(uuid)
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 主题
                .subject(SUBJECT)
                // 签发者
                .issuer(JWT_ISS)
                // 签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }


    // 获取token头部信息
    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    // 获取token负载信息
    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

    // 判断JWT是否过期
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // 检验token
    public static boolean checkToken(String token) {
        try {
            Jws<Claims> jws = JwtUtils.parseClaim(token);
            Claims claims = jws.getPayload();
            if (!isTokenExpired(claims)) {
                return true;
            }
        } catch (SignatureException e) {
            // 签名验证失败
            log.error("Failed to verify signature of JWT token: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Failed to parse JWT token: {}", e.getMessage());
        }
        return false;
    }
}