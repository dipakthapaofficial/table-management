package com.demo.table_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TableManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableManagementApplication.class, args);
    }

}
