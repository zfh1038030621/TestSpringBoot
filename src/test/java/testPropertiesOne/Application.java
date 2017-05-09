package testPropertiesOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    @Autowired
    TestProOne testProOne;

    @RequestMapping("/")
    public String greeting() {
        System.out.println(testProOne.getAge()+"||"+testProOne.getName()+"||"+testProOne.getAddr());
        return "Hello World!";
    }

    @RequestMapping("/test")
    public String test() {
        return "test success";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}