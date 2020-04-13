package com.zmg20200111.panda.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserDetailsService extends UserDetailsService {

    UserDetails getUserDetailByUserName(String username);
}
