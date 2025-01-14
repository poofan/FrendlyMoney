package com.broker.InvestmentApplication.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.broker.InvestmentApplication.model.AD_User;
import com.broker.InvestmentApplication.repository.UserRepository;

@Service
public class UserService
{
	private final UserRepository  userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public AD_User register(String email, String password)
	{
		if (userRepository.findByEmail(email).isPresent())
		{
			throw new RuntimeException("Email already registered");
		}

		AD_User user = new AD_User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));

		return userRepository.save(user);
	}

	public AD_User findByEmail(String email)
	{
		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public void save(AD_User user)
	{
		userRepository.save(user);
	}

	public boolean isPasswordValid(AD_User user, String password)
	{
		return passwordEncoder.matches(password, user.getPassword());
	}

	public void updateUserName(String email, String newName)
	{
		AD_User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Пользователь не найден."));
		user.setName(newName);
		userRepository.save(user);
	}

	public void updateUserPassword(String email, String currentPassword, String newPassword)
	{
		AD_User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Пользователь не найден."));

		if (!passwordEncoder.matches(currentPassword, user.getPassword()))
		{
			throw new RuntimeException("Текущий пароль указан неверно.");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

}
