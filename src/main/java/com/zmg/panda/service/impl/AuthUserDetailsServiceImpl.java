package com.zmg.panda.service.impl;

import com.zmg.panda.manage.bean.User;
import com.zmg.panda.manage.bean.UserRole;
import com.zmg.panda.service.AuthUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zmg.panda.db.VisualDB.USER_DB;

/**
 * @author Andy
 */
@Slf4j
@Service
public class AuthUserDetailsServiceImpl implements AuthUserDetailsService {

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
