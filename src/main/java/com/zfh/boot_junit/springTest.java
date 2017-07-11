package com.zfh.boot_junit;

import com.zfh.boot_propertyInjection.TestProOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangfanghu on 2017/5/25.
 * 使用spring-test进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= com.zfh.Application.class)// 指定spring-boot的启动类

public class springTest {
    @Autowired
    TestProOne testProOne;
    @Test
    public void test(){

        System.out.println(testProOne.getName());
    }
}
