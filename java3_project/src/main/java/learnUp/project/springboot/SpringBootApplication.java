package learnUp.project.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableCaching
@org.springframework.boot.autoconfigure.SpringBootApplication(/*exclude = SecurityAutoConfiguration.class*/)
public class SpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication.class, args);
    }
}
