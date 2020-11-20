package com.bd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.bd.*"})
@MapperScan("com.bd.repository")
@EnableTransactionManagement    //开启事务注解
@EnableDiscoveryClient
public class BdUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdUserServiceApplication.class, args);
    }

}
