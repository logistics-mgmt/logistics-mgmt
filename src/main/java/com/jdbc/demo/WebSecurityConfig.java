package com.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;


@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
	auth
	.inMemoryAuthentication()
	.withUser("admin")
	.password("admin").roles("USER");
}

@Override
protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
    .antMatchers("/index/**").access("hasRole('ROLE_USER')")
    .and()
        .formLogin().loginPage("/login").failureUrl("/login?error").loginProcessingUrl("/j_spring_security_check")
        .usernameParameter("username").passwordParameter("password")
    .and()
        .logout().logoutSuccessUrl("/login?logout")
    .and()
        .csrf();         
}

}