package com.lingfenglong.mydemo.security.custom.entity;

import com.lingfenglong.mydemo.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CustomUser extends org.springframework.security.core.userdetails.User {

    public CustomUser(User user) {
        super(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        user.getAuthorities() + ",ROLE_" + user.getRoles())
            );
    }
}
