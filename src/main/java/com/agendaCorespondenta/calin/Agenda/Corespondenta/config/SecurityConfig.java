package com.agendaCorespondenta.calin.Agenda.Corespondenta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth ->
						{
							auth.requestMatchers("/js/**", "/css/**", "/lh3.googleusercontent.com/**").permitAll();
							auth.anyRequest().authenticated();
						}
				)
				.oauth2Login(formLogin ->
						formLogin
								.loginPage("/login")
								.defaultSuccessUrl("/")
								.permitAll())
				.logout(logout ->
						logout.logoutSuccessUrl("/logout")
				)
				.build();
	}


	@Bean
	public WebSecurityCustomizer ignoreFilter() {
		return (web) -> web
				.ignoring()
				.requestMatchers(
						"/resources/**",
						"/static/**",
						"/css/**",
						"/fonts/**",
						"/img/**",
						"/error",
						"/api/data/**",
						"/actuator/**",
						"/contact/**"
				);
	}

}
