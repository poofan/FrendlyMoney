package com.broker.InvestmentApplication.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig
{
	@Bean
	public DateTimeFormatter dateTimeFormatter()
	{
		return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	}
}
