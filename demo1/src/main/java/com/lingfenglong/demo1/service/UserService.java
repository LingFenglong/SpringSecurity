package com.lingfenglong.demo1.service;

import com.lingfenglong.demo1.entity.User;
import com.lingfenglong.demo1.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
}
