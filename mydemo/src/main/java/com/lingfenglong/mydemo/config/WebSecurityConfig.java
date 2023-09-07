package com.lingfenglong.mydemo.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final ThymeleafViewResolver thymeleafViewResolver;

    public WebSecurityConfig(UserDetailsService userDetailsService, ThymeleafViewResolver thymeleafViewResolver) {
        this.userDetailsService = userDetailsService;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        return builder
                .parentAuthenticationManager(new ProviderManager())
                .authenticationProvider()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())

                .formLogin(config -> config
                        .loginPage("/login")
//                        .defaultSuccessUrl("/index")
                        .failureUrl("/error/403")
                        .successHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.setAttribute("user", request.getAttribute("username") + "666");
                            try {
                                thymeleafViewResolver
                                        .resolveViewName("index", Locale.CHINA)
                                        .render(null, request, response);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                )

                .logout(config ->config
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))

                .rememberMe(config -> config
                        .tokenValiditySeconds(60 * 60 * 24 * 3)
                        .rememberMeServices(
                                new TokenBasedRememberMeServices("123",
                                userDetailsService,
                                TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256)))

                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
