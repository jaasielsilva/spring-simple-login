package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    var userAdmin = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("admin123")
        .roles("ADMIN")
        .build();

    var userUser = User.withDefaultPasswordEncoder()
        .username("user")
        .password("user123")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(userAdmin, userUser);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/", true)
        .permitAll()
      )
      .logout(logout -> logout
        .logoutSuccessUrl("/login?logout")
        .permitAll()
      );
    return http.build();
  }
}
