package javawebdev.petsns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class PetsnsApplication{

    public static void main(String[] args) {
        SpringApplication.run(PetsnsApplication.class, args);
    }

}
