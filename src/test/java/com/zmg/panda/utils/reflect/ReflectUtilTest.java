package com.zmg.panda.utils.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class ReflectUtilTest {

    ReflectUtil reflectUtil = new ReflectUtil();

    @Test
    void listEntityFields() throws InvocationTargetException, IllegalAccessException {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount("123");
        userInfo.setUserName("钟名桂");
        reflectUtil.listEntityFields(userInfo);
    }

    @Test
    void listMapKeyValues() {
        Map<String, String> map = new HashMap<>();
        map.put("studentName", "诸葛大力");
        map.put("student_no", "24");
        map.put("user_name", "李白");
        map.put("account", "12345");
        reflectUtil.listMapKeyValues(map);
    }

    @Test
    void copyEntity() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> map = new HashMap<>();
        map.put("studentName", "诸葛大力");
        map.put("student_no", "24");
        map.put("user_name", "李白");
        map.put("account", "12345");
        UserInfo userInfo = reflectUtil.copyEntity(map, UserInfo.class);
        System.out.println(userInfo);
//        reflectUtil.listEntityFields(userInfo);
    }

    @Test
    void validateConstructor() {
        reflectUtil.validateConstructor(UserInfo.class);
    }
}