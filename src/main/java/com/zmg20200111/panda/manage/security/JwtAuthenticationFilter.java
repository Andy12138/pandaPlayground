package com.zmg20200111.panda.manage.security;

import com.zmg20200111.panda.manage.auth.JwtTokenProvider;
import com.zmg20200111.panda.manage.bean.AuthParametersConf;
import com.zmg20200111.panda.service.AuthUserDetailsService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andy
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthParametersConf authParametersConf;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtByRequest(request);
        if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token)) {
            String username = getUsernameByJwt(token, authParametersConf.getJwtTokenSecret());
            UserDetails userDetails = authUserDetailsService.getUserDetailByUserName(username);
            Authentication authenticaion = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticaion);
        } else {
            log.error("Token is null!");
        }
        super.doFilter(request, response, filterChain);
    }

    /**
     * 获取请求头中的token
     * @param request
     * @return
     */
    private String getJwtByRequest(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer")) {
            return token.replace("Bearer", "");
        }
        return null;
    }

    /**
     * 从token中获取用户名信息
     * @param token
     * @param signKey
     * @return
     */
    private String getUsernameByJwt(String token, String signKey) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
