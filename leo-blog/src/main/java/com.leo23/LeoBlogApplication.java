package com.leo23;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.leo23.mapper")
@EnableScheduling
@EnableSwagger2
public class LeoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeoBlogApplication.class, args);
    }
}
