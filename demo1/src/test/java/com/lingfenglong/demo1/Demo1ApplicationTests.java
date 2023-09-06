package com.lingfenglong.demo1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class Demo1ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void passwordEncodeTest() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }


}
