package com.zwy.ciserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 等于 @Configuration（Spring xml配置）+@EnableAutoConfiguration（自动配置）+@ComponentScan（可发现和装配一些bean）
@SpringBootApplication
@MapperScan("com.zwy.ciserver.dao")//不加这个mapper AutoWired会失败
public class CiserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiserverApplication.class, args);
    }

}

