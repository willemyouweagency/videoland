package com.demo.videoland.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


// This is file just to access the h2 console while still using bcrypt hashing in spring security
// Auto config would secure the h2 console. This is not production grade code
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic().disable();
        security.csrf().disable().authorizeRequests().anyRequest().permitAll();
        security.headers().frameOptions().disable();
    }
}
