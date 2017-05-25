package testPropertiesOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*  @SpringBootApplication = (默认属性)@Configuration + @EnableAutoConfiguration + @ComponentScan。  */
@RestController
public class Test {
    @Autowired
    TestProOne testProOne;

    @RequestMapping("/")
    public String greeting() {
        System.out.println(testProOne.getAge()+"||"+testProOne.getName()+"||"+testProOne.getAddr());
        return "Hello World!";
    }

}