package com.zmg20200111.panda.service.impl;

import com.zmg20200111.panda.manage.bean.User;
import com.zmg20200111.panda.manage.bean.UserRole;
import com.zmg20200111.panda.service.AuthUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Andy
 */
@Slf4j
@Service
public class AuthUserDetailsServiceImpl implements AuthUserDetailsService {

    /**
     * 模拟数据库用户，密码为123456
     */
    private static Map<String, User> USER_DB = new HashMap<>();
    static {
        log.info("模拟用户数据库赋值初始化");
        USER_DB.put("zmg", new User("zmg", "admin", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC", Collections.singletonList(new UserRole("zmg", "admin"))));
        USER_DB.put("lfq", new User("lfq", "staff", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC", Collections.singletonList(new UserRole("lfq", "staff"))));
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (USER_DB.get(username) == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在！");
        }
        User loginUser = USER_DB.get(username);
        // 根据用户名获取角色列表
        List<String> userCodeRoles = loginUser.getRoles().stream().map(UserRole::getRoleCode).collect(Collectors.toList());

        List<GrantedAuthority> authorities = userCodeRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(username, loginUser.getPassword(), authorities);
    }

    @Override
    public UserDetails getUserDetailByUserName(String username) {
        return this.loadUserByUsername(username);
    }
}
