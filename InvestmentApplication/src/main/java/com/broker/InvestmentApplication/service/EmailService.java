package com.broker.InvestmentApplication.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService
{
	public void sendEmail(String to, String subject, String body)
	{
		System.out.printf("Email sent to %s with subject %s: %s%n", to, subject, body);
	}
}
