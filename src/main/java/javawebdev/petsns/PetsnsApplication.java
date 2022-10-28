package javawebdev.petsns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PetsnsApplication{

    public static void main(String[] args) {
        SpringApplication.run(PetsnsApplication.class, args);
    }

}
