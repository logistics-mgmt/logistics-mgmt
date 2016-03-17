package com.jdbc.demo;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by Mateusz on 05-Dec-15.
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
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
		/*
		 * Registering OpenSessionInViewFilter to enable use of lazy initialized
		 * collections in Spring MVC controllers.
		 */
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(osivFilter());
		registration.addUrlPatterns("/*");
		registration.setName("osivFilter");

		/*
		 * Defining sessionFactoryBeanName, perhaps renaming
		 * hibernateSessionFactory to sessionFactory in beans.xml would be a
		 * better solution. TODO: refactor beans.xml - use default name for
		 * SessionFactory etc.
		 */
		registration.addInitParameter("sessionFactoryBeanName", "hibernateSessionFactory");
		return registration;
	}

	@Bean(name = "osivFilter")
	public OpenSessionInViewFilter osivFilter() {
		return new OpenSessionInViewFilter();
	}
}
