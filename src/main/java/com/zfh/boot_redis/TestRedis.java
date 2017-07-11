package com.zfh.boot_redis;

import com.zfh.boot_redis.templates.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.sun.scenario.Settings.set;

/**
 * Created by zhangfanghui on 2017/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.zfh.Application.class)
public class TestRedis {
    @Autowired private RedisTemplate<String, String> redisTemplate;
    @Resource
    private Cache testTemplate;
    @Test
    public void redis() {
        redisTemplate.opsForValue().set("12", "a");
        System.out.println(redisTemplate.opsForValue().get("12"));

        testTemplate.getCacheRedis().opsForValue().set("16", "c");
        System.out.println("************************************************************");
        System.out.println(testTemplate.getCacheRedis().opsForValue().get("16"));
        System.out.println("************************************************************");
    }

}
