package com.pandar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.pandar.modules.sys.mapper")
@SpringBootApplication(scanBasePackages = {"com.pandar.*", "cn.hutool.extra.spring"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}