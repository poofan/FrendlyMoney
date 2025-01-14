package com.broker.InvestmentApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.broker.InvestmentApplication.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig
{
	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/css/**", "/js/**", "/register", "/api/auth/register", "/api/auth/login")
						.permitAll().anyRequest().authenticated()
				).formLogin(form -> form.defaultSuccessUrl("/", true)
				).logout(logout -> logout.logoutSuccessUrl("/")
				);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}