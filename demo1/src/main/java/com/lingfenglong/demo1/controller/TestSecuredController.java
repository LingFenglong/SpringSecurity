package com.lingfenglong.demo1.controller;

import com.lingfenglong.demo1.entity.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestSecuredController {

//    @Secured({"admin", "manager", "ROLE_developer"})
//    @PreAuthorize("hasAnyAuthority('admin', 'ROLE_salesman')")
    @PostAuthorize("hasAuthority('admin')")
    @GetMapping("/secured/test")
    public String test() {
        System.out.println("方法执行了》》》");
        return "secured pass";
    }

    @PostFilter("filterObject.authorities.equals('user')")
    @GetMapping("/secured/test/postfilter")
    public List<User> postfilter () {
        System.out.println("方法执行了》》》");

        List<User> userList = Arrays.asList(
                new User(1, "zhangsan", "secret", "user", "secret"),
                new User(2, "lisi", "secret", "secret", "secret"),
                new User(3, "wangwu", "secret", "user", "secret")
        );
        userList.forEach(System.out::println);

        return new ArrayList<>(userList);
    }

    @PreFilter("filterObject.id % 2 == 0")
    @PostMapping("/secured/test/prefilter")
    public List<User> prefilter (@RequestBody List<User> userList) {
//        userList.stream()
//                .filter(user -> user.getId() % 2 == 0)
//                .forEach(System.out::println);

        System.out.println("方法执行了》》》");

        userList.forEach(System.out::println);
        return userList;
    }
    /*
        [
            {
                "id": 1,
                "username": "zhangsan",
                "password": "secret",
                "authorities": "user",
                "roles": "secret"
            },
            {
                "id": 2,
                "username": "lisi",
                "password": "secret",
                "authorities": "user",
                "roles": "secret"
            },
            {
                "id": 3,
                "username": "wangwu",
                "password": "secret",
                "authorities": "user",
                "roles": "secret"
            }
        ]
     */
}
