package com.zfh.boot_redis;

import com.zfh.boot_redis.templates.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhangfanghui on 2017/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.zfh.Application.class)
public class TestRedisTwo {
    @Resource
    private Cache testTemplate;
    @Test
    public void test(){
        testTemplate.setNS("zzz","43");
        System.out.println("************************************************************");
        System.out.println(testTemplate.getNS("zzz"));
        System.out.println("************************************************************");


        testTemplate.put("qqq","43");
        System.out.println("************************************************************");
        System.out.println(testTemplate.get("qqq"));
        System.out.println("************************************************************");
    }

}