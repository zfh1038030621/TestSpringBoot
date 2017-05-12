package redis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testPropertiesOne.Application;

import static java.awt.Color.red;

/**
 * Created by zhangfanghui on 2017/5/12.
 */
@SpringBootApplication
@RestController
public class TestRedis {
    @Autowired private RedisTemplate<String, String> redisTemplate;
    public static  void main(String[] arge){

        SpringApplication.run(TestRedis.class, arge);
    }
    @RequestMapping("/redis")
    public String redis() {
        System.out.println("31");
        redisTemplate.opsForValue().set("12", "a");
        System.out.println(redisTemplate.opsForValue().get("12"));
        return "Hello World!";
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
