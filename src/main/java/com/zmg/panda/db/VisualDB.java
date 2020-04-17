package com.zmg.panda.db;

import com.zmg.panda.manage.bean.User;
import com.zmg.panda.manage.bean.UserRole;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VisualDB {
    /**
     * 模拟数据库用户，密码为123456
     */
    public static Map<String, User> USER_DB = new HashMap<>();
    public static Map<String, String> TOKEN_DB = new HashMap<>();
    static {
        log.info("模拟用户数据库赋值初始化");
        USER_DB.put("zmg", new User("zmg", "admin", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC", Collections.singletonList(new UserRole("zmg", "admin"))));
        USER_DB.put("lfq", new User("lfq", "staff", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC", Collections.singletonList(new UserRole("lfq", "staff"))));
    }
}
