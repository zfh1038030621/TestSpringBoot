package com.zfh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhangfanghui on 2017/5/23.
 */
@SpringBootApplication
/*扫描注解的范围 ，SpringBootApplication里面是包含ComponentScan这个注解的,所以要重写一下，
要不然扫描注解范围的值就是空*/
@ComponentScan("redis,testPropertiesOne")
@ImportResource(locations={"classpath:cache-context.xml"})
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
