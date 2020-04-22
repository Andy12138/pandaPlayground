package com.zmg.panda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void listTest() {
        Long size = redisTemplate.opsForList().size("user:zmg");
        System.out.println("长度为：" + size);
        redisTemplate.opsForList().leftPush("user:zmg", "66");
//        redisTemplate.opsForList().leftPush("user:zmg", "88888");
        redisTemplate.opsForList().remove("user:zmg", 0, null);
        size = redisTemplate.opsForList().size("user:zmg");

        List<String> range = redisTemplate.opsForList().range("user:zmg", 0, size - 1);
        range.forEach(str -> System.out.println(str));
    }

    @Test
    public void removeTest() {
        //保留集合中索引0，0之间的值，其余全部删除  所以list只有有一个值存在
        redisTemplate.opsForList().trim("user:zmg",0,0);
        //将list中的剩余的一个值也删除
        redisTemplate.opsForList().leftPop("user:zmg");
    }
}
