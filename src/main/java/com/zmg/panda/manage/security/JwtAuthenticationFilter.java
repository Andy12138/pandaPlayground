package com.zmg.panda.manage.security;

import com.zmg.panda.service.AuthUserDetailsService;
import com.zmg.panda.manage.auth.JwtTokenProvider;
import com.zmg.panda.manage.bean.AuthParametersConf;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.zmg.panda.manage.auth.JwtTokenProvider.TOKEN_PREFIX;

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
            UsernamePasswordAuthenticationToken authentications = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentications.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentications);
            // 将登陆用户存入session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
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
        if (StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
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
