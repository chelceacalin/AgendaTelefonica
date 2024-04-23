package com.agendatelefonica.calin.Agenda.Telefonica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth ->
						{
							auth.anyRequest().authenticated();
						}
				)
				.oauth2Login(formLogin ->
						formLogin
								.loginPage("/login")
								.permitAll())
				.logout(logout -> {
					logout.logoutSuccessUrl("/logout");
				})
				.build();
	}
}
