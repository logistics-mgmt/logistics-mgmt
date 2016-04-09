package com.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.jdbc.demo.converter.RoleConverter;

@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	RoleConverter roleConverter;

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public FilterRegistrationBean osivFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(osivFilter());
		registration.addUrlPatterns("/*");
		registration.setName("osivFilter");

		registration.addInitParameter("sessionFactoryBeanName", "hibernateSessionFactory");
		return registration;
	}

	@Bean(name = "osivFilter")
	public OpenSessionInViewFilter osivFilter() {
		return new OpenSessionInViewFilter();
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleConverter);
	}


}
