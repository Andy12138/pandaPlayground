package com.zmg.panda.manage.auth;

import com.zmg.panda.manage.bean.AuthParametersConf;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Andy
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private AuthParametersConf authParametersConf;

    public String createToken(Authentication authentication) {
        // username
        String username = ((User) authentication.getPrincipal()).getUsername();
        // expire time
        Date expireTime = new Date(System.currentTimeMillis() + authParametersConf.getTokenExpiredMs());
        // create token
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expireTime)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, authParametersConf.getJwtTokenSecret())
                .compact();
    }

    /**
     * parseClaimsJws 有5个异常，这里都统一处理了----->
     * 1）ExpiredJwtException token时效过期异常
     * 2）UnsupportedJwtException 验证的token和期待的token格式不一样时，例如解析的是一个明文JWT而期待的是一个加密签名JWT时就会抛出这个异常。
     * 3）MalformedJwtException 表示这不是一个正确方法创建的token。
     * 4）SignatureException token签名验证失败异常
     * 5）IllegalArgumentException token为null或者空异常
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(authParametersConf.getJwtTokenSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("token校验失败: {}", e.getMessage());
            return false;
        }
    }
}
