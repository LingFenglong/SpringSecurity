package com.lingfenglong.mydemo;

import com.lingfenglong.mydemo.utils.DatabaseUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MydemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MydemoApplication.class, args);
        DatabaseUtil databaseUtil = context.getBean(DatabaseUtil.class);
        databaseUtil.setSearchPath("SpringSecurity");
    }

}
