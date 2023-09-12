package com.lingfenglong.mydemo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                             UserDetailsService userDetailsService,
                             DataSource dataSource) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        repository.setCreateTableOnStartup(false);  // 不自动建表

        return repository;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new PersistentTokenBasedRememberMeServices(
                "123456",
                userDetailsService,
                persistentTokenRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(config -> config
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index")
                        .failureUrl("/error/403")
                        .permitAll()
                )

                .logout(config ->config
                        .logoutUrl("/logout") // default is "/logout"
                        // csrf开启只能使用post方法logout
                        .logoutSuccessUrl("/login")
                        .permitAll())

                .rememberMe(config -> config
                        .tokenValiditySeconds(60 * 60 * 24 * 3)
                        .rememberMeServices(rememberMeServices()))

                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
        ;

        return http.build();
    }
}
