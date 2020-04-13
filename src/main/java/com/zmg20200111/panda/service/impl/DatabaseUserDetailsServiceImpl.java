package com.zmg20200111.panda.service.impl;

import com.zmg20200111.panda.bean.User;
import com.zmg20200111.panda.bean.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Andy
 */
@Service
public class DatabaseUserDetailsServiceImpl implements UserDetailsService {

    /**
     * 模拟数据库用户，密码为123456
     */
    private static final User LOGIN_USER = new User("zmg", "admin", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC", Collections.singletonList(new UserRole("zmg", "1")));

    private static final Map<String, List<String>> LOGIN_USER_ROLE = Collections.singletonMap("zmg", Collections.singletonList("1"));

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!LOGIN_USER.getUsername().equals(username)) {
            throw new UsernameNotFoundException("用户" + username + "不存在！");
        }
        User loginUser = LOGIN_USER;
        // 根据用户名获取角色列表
        List<String> userCodeRoles = LOGIN_USER_ROLE.get(username);

        List<GrantedAuthority> authorities = userCodeRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, loginUser.getPassword(), authorities);
        return userDetails;
    }
}
