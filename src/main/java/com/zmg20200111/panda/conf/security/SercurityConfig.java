package com.zmg20200111.panda.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SercurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private ImChe

    protected void configure(AuthenticationManagerBuilder auth) {
        addProvider(auth);
    }

    private void addProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.imAuthenticationProvider());
    }

    private AuthenticationProvider imAuthenticationProvider() {
//        return new ImAuthenticationProvider()
        return null;
    }
}
