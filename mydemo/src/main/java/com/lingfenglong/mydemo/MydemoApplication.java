package com.lingfenglong.mydemo;

import com.lingfenglong.mydemo.config.DatabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class MydemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MydemoApplication.class, args);
        context.getBean(DatabaseConfig.class)
                .setSearchPath("SpringSecurity");


        new ProviderManager()
//        new AuthenticationManagerBuilder(new ObjectPostProcessor<>() {
//            @Override
//            public <O> O postProcess(O object) {
//                return object;
//            }
//        })
//                .
//                .build()
    }

}
