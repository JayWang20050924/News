package com.news.backendproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.news.backendproject.mapper")
public class NewsBackendProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsBackendProjectApplication.class, args);
    }
}
