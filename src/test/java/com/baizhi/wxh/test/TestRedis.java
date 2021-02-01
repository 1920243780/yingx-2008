package com.baizhi.wxh.test;

import com.baizhi.wxh.YingxApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = YingxApplication.class)
@RunWith(SpringRunner.class)
public class TestRedis {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void setRedisTemplate(){
        ValueOperations opsForValue = redisTemplate.opsForValue();

        opsForValue.set("name","123456");
    }
}
