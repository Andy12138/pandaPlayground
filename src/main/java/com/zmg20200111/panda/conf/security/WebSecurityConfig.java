package com.zmg20200111.panda.conf.security;

import com.zmg20200111.panda.manage.security.MyAudhenticationFailHandler;
import com.zmg20200111.panda.manage.security.MyAudhenticationSuccessHandler;
import com.zmg20200111.panda.manage.security.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Andy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private com.zmg20200111.panda.service.impl.DatabaseUserDetailsServiceImpl databaseUserDetailsService;
    @Autowired
    private MyAudhenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAudhenticationFailHandler myAudhenticationFailHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(databaseUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // 关闭跨域验证
                .and().csrf().disable()
                .authorizeRequests()
                // /api/root/** 的接口全部放行
                .antMatchers("/api/root/**").permitAll()
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
}
