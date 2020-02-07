package org.vaadin.alump.auth0demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Auth0DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Auth0DemoApplication.class, args);
	}

	@Autowired
	private ApplicationContext ctx;

	@Bean
	public ServletRegistrationBean anotherServlet() {
		return new ServletRegistrationBean(new Auth0Servlet(ctx), "/*");
	}
}
