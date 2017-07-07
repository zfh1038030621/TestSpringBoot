package spring_junit;

import com.zfh.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import testPropertiesOne.TestProOne;

/**
 * Created by zhangfanghu on 2017/5/25.
 * 使用spring-test进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)// 指定spring-boot的启动类

public class springTest {
    @Autowired
    TestProOne testProOne;
    @Test
    public void test(){

        System.out.println(testProOne.getName());
    }
}
