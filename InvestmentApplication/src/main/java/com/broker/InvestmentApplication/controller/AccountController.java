package com.broker.InvestmentApplication.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.broker.InvestmentApplication.dto.TransactionDto;
import com.broker.InvestmentApplication.model.AD_User;
import com.broker.InvestmentApplication.service.EmailService;
import com.broker.InvestmentApplication.service.TransactionService;
import com.broker.InvestmentApplication.service.UserService;

@Controller
public class AccountController
{
	private final UserService        userService;
	private final TransactionService transactionService;
	private final EmailService       emailService;
	private String email = null;
	
	public AccountController(UserService userService, TransactionService transactionService, EmailService emailService)
	{
		this.userService = userService;
		this.transactionService = transactionService;
		this.emailService = emailService;
	}

	@GetMapping("/account")
	public String accountPage(Model model)
	{
		// Получаем текущего аутентифицированного пользователя
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    email = authentication.getName(); // Получаем email из имени пользователя
	    
	    // Ищем пользователя в базе данных
	    AD_User user = userService.findByEmail(email);

	    List<TransactionDto> transactionsDto = transactionService.getTransactionsForUser(email).stream()
				.map(transaction ->
				{
					TransactionDto transactionDto = new TransactionDto();
					
					transactionDto.setAmount(transaction.getAmount());
					transactionDto.setEmail(transaction.getEmail());
					
					// Создание форматтера
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

			        // Форматирование LocalDateTime в строку
			        String formattedDate = transaction.getTimestamp().format(formatter);
					
					transactionDto.setTimestamp(formattedDate);
					
					return transactionDto;
				})
				.collect(Collectors.toList());
	    
	    // Передаём данные в модель
	    model.addAttribute("balance", user.getBalance());
	    model.addAttribute("transactions", transactionsDto);

	    return "account";
	}

	@PostMapping("/account/recharge")
	public String recharge(@RequestParam Double amount, Model model)
	{	    
		AD_User user = userService.findByEmail(email);
		
		user.setBalance(user.getBalance() + amount);
		userService.save(user);

		transactionService.saveTransaction(email, amount, "Пополнение");
		emailService.sendEmail(email, "Баланс пополнен", "Ваш баланс пополнен на сумму " + amount);

		model.addAttribute("balance", user.getBalance());
		model.addAttribute("message", "Ваш баланс пополнен на сумму " + amount);
		
		return "account";
	}
}
