package com.jdbc.demo;

/**
 * Created by Mateusz on 30-Nov-15.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@ImportResource("classpath:/beans.xml")
@EnableTransactionManagement
@Transactional(transactionManager = "txManager")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
