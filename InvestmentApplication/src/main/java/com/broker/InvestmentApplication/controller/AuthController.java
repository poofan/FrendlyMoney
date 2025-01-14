package com.broker.InvestmentApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broker.InvestmentApplication.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	private final UserService userService;

	public AuthController(UserService userService)
	{
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password)
	{
		userService.register(email, password);
		
		return ResponseEntity.ok("User registered successfully");
	}
}
