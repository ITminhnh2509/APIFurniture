package com.project.furniture.config;

import com.project.furniture.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    //    @Bean
//    public UserDetailsManager userDetailsManager(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withUsername("Nai1").password(passwordEncoder().encode("Nai1")).roles("USER").build());
//        manager.createUser(
//                User.withUsername("Nai2").password(passwordEncoder().encode("Nai2")).roles("ADMIN").build()
//        );
//        return manager;
//    }
    private final UserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByUsername(username);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET,"/api/user/list/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET,"/api/user/login").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/user/create").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable();
        return http.build();
    }
}
