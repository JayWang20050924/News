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

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    // 生成签名密钥
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 生成JWT令牌（不变）
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuer(issuer)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 从令牌中获取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * @param token 令牌
     * @param username 预期的用户名
     * @return 有效返回true，否则false（抛出异常时返回false）
     */
    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            // 验证：用户名一致 + 令牌未过期 + 发行人匹配
            return extractedUsername.equals(username)
                    && !isTokenExpired(token)
                    && extractIssuer(token).equals(issuer);
        } catch (ExpiredJwtException e) {
            // 令牌过期
            System.err.println("JWT expired: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            // 不支持的JWT格式
            System.err.println("Unsupported JWT: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            // 令牌格式错误/篡改
            System.err.println("Malformed JWT: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            // 签名验证失败（核心：防止伪造）
            System.err.println("JWT signature invalid: " + e.getMessage());
            return false;
        }
    }

    // -------------- 补充工具方法 --------------
    // 提取发行人
    private String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    // 检查令牌是否过期
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 从令牌中获取过期时间
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 提取所有载荷
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 解析令牌获取所有载荷
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}