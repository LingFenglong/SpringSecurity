package com.lingfenglong.mydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
public class DatabaseConfig {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public  void setSearchPath(String... paths) {
        if (paths == null || paths.length == 0)
            throw new IllegalArgumentException();

        String searchPaths = Arrays.toString(paths);
        searchPaths = searchPaths.substring(1, searchPaths.length() - 1);
//        SET search_path TO "SpringSecurity";
        jdbcTemplate.execute("set search_path to \"" + searchPaths + "\"");
    }
}
