package com.lingfenglong.demo1.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("\"SpringSecurity\".t_user")     // 指定 模式 和 表名，PostgreSQL的模式要使用双引号
public class User {
    private Integer id;
    private String username;
    private String password;
    private String authorities;
    private String roles;
}
