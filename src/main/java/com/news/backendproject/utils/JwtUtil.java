package com.news.backendproject.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // 从配置文件读取参数
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    // 生成签名密钥（基于配置的secretKey，jwt要求密钥长度足够，否则会报错）
    private SecretKey getSigningKey() {
        // 使用HS256算法时，密钥需至少256位（32字节），此处通过Keys.hmacShaKeyFor转换
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成JWT令牌
     * @param username 用户名（或用户唯一标识）
     * @return 生成的令牌
     */
    public String generateToken(String username) {
        // 可选：添加自定义载荷（如角色、权限等）
        Map<String, Object> claims = new HashMap<>();
        // claims.put("role", "USER"); // 示例：添加角色信息

        return Jwts.builder()
                // 设置自定义载荷
                .setClaims(claims)
                // 设置主题（通常为用户名/用户ID）
                .setSubject(username)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 设置过期时间（当前时间 + 过期毫秒数）
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // 设置发行人
                .setIssuer(issuer)
                // 设置签名算法和密钥
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                // 压缩为字符串
                .compact();
    }

    /**
     * 从令牌中获取用户名（主题）
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 验证令牌是否有效（检查签名和过期时间）
     * @param token 令牌
     * @param username 预期的用户名（用于二次验证）
     * @return 有效返回true，否则false
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        // 验证用户名一致且令牌未过期
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // -------------- 内部工具方法 --------------

    // 从令牌中提取指定的声明
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 解析令牌，获取所有载荷
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 用相同的密钥解析
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查令牌是否过期
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 从令牌中获取过期时间
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
