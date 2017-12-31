package com.hz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 短信平台主文件
 * Created by huangzhe on 2017/12/8.
 */
@SpringBootApplication
@ComponentScan({"com.hz"})
public class TbiSmsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TbiSmsApiApplication.class, args);
    }
}
