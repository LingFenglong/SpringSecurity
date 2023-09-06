package com.lingfenglong.demo1.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityWebConfig {
//    private final DataSourceProperties dataSourceProperties;
    private final UserDetailsService userDetailsService;

    public SecurityWebConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean("tokenRepositoryDataSource")
//    public DataSource tokenRepositoryDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
////        jdbc:postgresql://localhost:5432/Spring?currentSchema=SpringSecurity
//        dataSource.setJdbcUrl(dataSourceProperties.getUrl() + "?currentSchema=SpringSecurity");
//        dataSource.setUsername(dataSourceProperties.getUsername());
//        dataSource.setPassword(dataSourceProperties.getPassword());
//        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        System.err.println(new JdbcTemplate(dataSource)
//                .queryForObject("select current_schema", String.class));
//        return dataSource;
//    }

//    @Bean
//    public PersistentTokenRepository jdbcTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(tokenRepositoryDataSource());
////        tokenRepository.setCreateTableOnStartup(true); 自动创建 token 表
//        return tokenRepository;
//    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(
                        "123456", userDetailsService, TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256);
        rememberMeServices.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setCookieName("remember-me"); // 默认值即为 remember-me
        return rememberMeServices;
    }
    @Bean
    AuthenticationManager theAuthenticationManager() {
        return new ProviderManager();
    }
    @Bean
    RememberMeAuthenticationFilter rememberMeFilter() {
        return new RememberMeAuthenticationFilter(theAuthenticationManager(), rememberMeServices());
    }
    @Bean
    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("123456");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(config ->  config
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/test/index")
                        .failureUrl("/login_error"))

                .logout(config -> config
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))

                .rememberMe(config -> config
                        // 设置 tokenRepository
//                        .tokenRepository(jdbcTokenRepository())
                        .rememberMeServices(rememberMeServices())
                        // 有效时长 7d
                        .tokenValiditySeconds(60 * 60 * 24 * 7))

                .authorizeHttpRequests(config -> config
                        .requestMatchers("/test/hello/", "/login")
                        .permitAll()

                        .requestMatchers("/test/admin")
                        .hasAuthority("admin")

                        .requestMatchers("/test/manager")
                        .hasAnyAuthority("admin", "manager")

                        .requestMatchers("/test/user")
                        .hasAnyAuthority("admin", "manager", "user")

                        .requestMatchers("/test/salesman")
                        .hasAnyRole("boss", "salesman")

                        .requestMatchers("/test/developer")
                        .hasAnyRole("boss", "developer")

                        .anyRequest()
                        .authenticated())

                .exceptionHandling(config -> config
                        .accessDeniedPage("/error/403"))

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        // 内存用户管理器
//        UserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails lisi = User
//                .builder()
//                .username("lisi")
//                .password("111111")
//                .roles("USER")
//                .passwordEncoder(passwordEncoder::encode)
//                .build();
//        // 创建用户 lisi
//        manager.createUser(lisi);
//        return manager;
//    }
}