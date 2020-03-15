package com.circ.microservices.registration.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class RegistrationWebSecurity extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/eureka/**").permitAll()
		.antMatchers("/actuator/**").permitAll()
		.antMatchers("/registrations/**").permitAll()
		.antMatchers("/sellers/**").permitAll()
		;
        super.configure(http);
	}
}
