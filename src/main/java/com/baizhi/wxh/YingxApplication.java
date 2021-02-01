package com.baizhi.wxh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.wxh.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.wxh.dao")
@SpringBootApplication
public class YingxApplication {
    public static void main(String[] args) {
        SpringApplication.run(YingxApplication.class,args);
    }
}
