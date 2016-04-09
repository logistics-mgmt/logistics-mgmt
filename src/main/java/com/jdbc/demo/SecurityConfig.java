	package com.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	 	@Autowired
	    @Qualifier("customUserDetailsService")
	    UserDetailsService userDetailsService;
	     
	    @Autowired
	    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService);
	    }
	    
	    @Configuration
	    @Order(1)
	    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	        protected void configure(HttpSecurity http) throws Exception {
	            http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated().and()
	                    .httpBasic().and().csrf().disable();
	        }
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/drivers/add").access("hasRole('FORWARDER')")
					.antMatchers("/drivers/edit/**").access("hasRole('FORWARDER')")
					.antMatchers("/vehicles/add").access("hasRole('FORWARDER')")
					.antMatchers("/vehicles/edit/**").access("hasRole('FORWARDER')")
					.antMatchers("/clients/add").access("hasRole('FORWARDER')")
					.antMatchers("/clients/edit/**").access("hasRole('FORWARDER')")
					.antMatchers("/transports/add").access("hasRole('FORWARDER')")
					.antMatchers("/transports/edit/**").access("hasRole('FORWARDER')")
					.antMatchers("/users/**").access("hasRole('ADMIN')")
					.antMatchers("/**").access("hasAnyRole('USER','FORWARDER', 'ADMIN')")
					.anyRequest().authenticated()
					.and().formLogin().loginPage("/login").permitAll()
		  			.usernameParameter("login").passwordParameter("password")
					.and().csrf() 
			        .and().exceptionHandling().accessDeniedPage("/access_denied");
			
	   }
	
}
	 
