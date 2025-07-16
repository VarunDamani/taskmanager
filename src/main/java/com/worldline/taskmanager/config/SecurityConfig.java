package com.worldline.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.developer-password}")
    private String developerPassword;

    @Bean
    public UserDetailsService users() {
        // Configure DEVELOPER user with TASK_GROUP_EDITOR role
        // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html
        UserDetails user = User.builder()
                .username("DEVELOPER")
                .password("{bcrypt}".concat(developerPassword))
                .roles("TASK_GROUP_EDITOR")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Add cors support
                .cors(Customizer.withDefaults())

                // Make stateless
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Setup authorization and permissions
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(HttpMethod.GET,
                                "/healthz",
                                "/swagger-ui/**",
                                "/v3/api-docs*/**").permitAll()
                        .anyRequest().authenticated())

                // Configure http basic auth
                // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
