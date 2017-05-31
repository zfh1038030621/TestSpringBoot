package spring_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by zhangfanghu on 2017/5/25.
 * 使用spring-test进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class springTest {
    @Test
    public void test(){

        System.out.println("12");
    }
}
