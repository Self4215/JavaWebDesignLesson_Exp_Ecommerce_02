package org.self4215.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 密码加密器（Spring 容器管理）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 安全过滤链：配置登录、权限、退出等规则
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF（新手简化，生产环境需开启）
                .csrf().disable()
                // 配置请求权限
                .authorizeHttpRequests(auth -> auth
                        // 登录页、注册页、静态资源允许匿名访问
                        .antMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                        // 其他所有请求需要登录
                        .anyRequest().authenticated()
                )
                // 配置登录页面
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页路径
                        .defaultSuccessUrl("/products", true) // 登录成功后跳转到商品列表
                        .permitAll()
                )
                // 配置退出登录
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // 退出后跳回登录页
                        .permitAll()
                );

        return http.build();
    }
}