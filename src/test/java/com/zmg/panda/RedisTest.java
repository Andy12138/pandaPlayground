package com.zmg.panda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void listTest() {
            redisTemplate.delete(redisTemplate.keys("user:*"));
//        redisTemplate.opsForList().rightPush("user:zmg:1", "77");
//        redisTemplate.opsForList().rightPush("user:zmg:1", "66");
//        redisTemplate.opsForList().rightPush("user:zmg:1", "77");
//        redisTemplate.opsForList().rightPush("user:zmg:2", "88");
        Long size = redisTemplate.opsForList().size("user:zmg");
//        List<String> range = redisTemplate.opsForList().range("user:zmg", 0, size - 1);
//        range.forEach(System.out::println);
//        System.out.println("---->");
//        redisTemplate.opsForList().remove("user:zmg", 0, "77");
//        range = redisTemplate.opsForList().range("user:zmg", 0, size - 1);
//        range.forEach(System.out::println);
    }

    @Test
    public void removeTest() {
        //保留集合中索引0，0之间的值，其余全部删除  所以list只有有一个值存在
        redisTemplate.opsForList().trim("user:zmg",0,0);
        //将list中的剩余的一个值也删除
        redisTemplate.opsForList().leftPop("user:zmg");
    }

    @Test
    public void addTest() {
        redisTemplate.opsForHash().put("zmgMap", "zmg", "zmgBody");
        redisTemplate.opsForHash().put("zmgMap", "lfq", "lfqBody");
        Object o = redisTemplate.opsForHash().get("zmgMap", "zmg");
        System.out.println(o);
        redisTemplate.opsForHash().delete("zmgMap", "zmg");
        System.out.println(redisTemplate.opsForHash().hasKey("zmgMap", "zmg"));
        System.out.println(redisTemplate.opsForHash().hasKey("zmgMap", "lfq"));
        redisTemplate.opsForHash().delete("zmgMap", "zmg");
        o = redisTemplate.opsForHash().get("zmgMap", "zmg");
        System.out.println(o);
        o = redisTemplate.opsForHash().get("zmgM1ap", "zmg");
        System.out.println(o);
        redisTemplate.delete("zmgMap");
    }
}
