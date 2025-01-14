package com.broker.InvestmentApplication.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.broker.InvestmentApplication.service.UserService;

@Controller
public class SettingsController
{

	private final UserService userService;

	public SettingsController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping("/settings")
	public String settingsPage(Model model)
	{
		model.addAttribute("message", "");
		model.addAttribute("success", true); // Начальное значение
		return "settings";
	}

	@PostMapping("/settings/update-name")
	public String updateName(@RequestParam String name, Principal principal, Model model)
	{
		try
		{
			userService.updateUserName(principal.getName(), name);
			model.addAttribute("message", "Имя успешно обновлено!");
			model.addAttribute("success", true);
		}
		catch (Exception e)
		{
			model.addAttribute("message", "Ошибка при обновлении имени.");
			model.addAttribute("success", false);
		}
		return "settings";
	}

	@PostMapping("/settings/update-password")
	public String updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword,
			Principal principal, Model model)
	{
		try
		{
			userService.updateUserPassword(principal.getName(), currentPassword, newPassword);
			model.addAttribute("message", "Пароль успешно обновлён!");
			model.addAttribute("success", true);
		}
		catch (Exception e)
		{
			model.addAttribute("message", "Ошибка при обновлении пароля: " + e.getMessage());
			model.addAttribute("success", false);
		}
		return "settings";
	}
}
