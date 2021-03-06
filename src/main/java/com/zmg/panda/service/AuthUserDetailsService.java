package com.zmg.panda.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthUserDetailsService extends UserDetailsService {

    UserDetails getUserDetailByUserName(String username);
}
