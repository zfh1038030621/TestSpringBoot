package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangfanghui on 2017/5/12.
 */
@RestController
public class TestRedis {
    @Autowired private RedisTemplate<String, String> redisTemplate;
    @RequestMapping("/redis")
    public String redis() {
        redisTemplate.opsForValue().set("12", "a");
        System.out.println(redisTemplate.opsForValue().get("12"));
        return "redis!";
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
