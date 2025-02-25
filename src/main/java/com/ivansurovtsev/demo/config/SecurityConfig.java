package com.ivansurovtsev.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll() // Доступ всем
                        .requestMatchers("/student/**").hasAuthority("STUDENT") // Только студенты
                        .requestMatchers("/teacher/**").hasAuthority("TEACHER") // Только преподаватели
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Только администраторы
                        .anyRequest().authenticated() // Остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}