package com.lingfenglong.mydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())

                .formLogin(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/index")
                        .failureUrl("/error/403")
                        .successHandler((request, response, authentication) -> {
                            request.setAttribute("user", request.getAttribute("user"));
                        })
                )

                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}