package com.zmg.panda.conf.security;

import com.zmg.panda.manage.security.JwtAuthenticationFilter;
import com.zmg.panda.manage.security.MyAudhenticationFailHandler;
import com.zmg.panda.manage.security.MyAudhenticationSuccessHandler;
import com.zmg.panda.manage.security.MyAuthenticationEntryPoint;
import com.zmg.panda.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Andy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHIT_ELIST = {
//            swagger
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/swagger-resources",
            "/v2/api-docs",
            "/webjars/**",
            "/csrf",
            "/druid/**",
            "configuration/ui",
            "configuration/security",

            "/api/panda/**",
            "/api/websocket/**"
    };

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private MyAudhenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAudhenticationFailHandler myAudhenticationFailHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 关闭seesion创建和使用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 关闭跨域验证
                .and().csrf().disable()
                .authorizeRequests()
                // /api/root/** 的接口全部放行
                .antMatchers(AUTH_WHIT_ELIST).permitAll()
                .anyRequest().authenticated()
                // post类型的登录连接
                .and().formLogin().loginProcessingUrl("/api/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAudhenticationFailHandler)
                .and().exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/test/springWebSocketStomp.html", "/favicon.ico");
    }
}
