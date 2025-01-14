package com.broker.InvestmentApplication.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.broker.InvestmentApplication.dto.TransactionDto;
import com.broker.InvestmentApplication.model.Transaction;
import com.broker.InvestmentApplication.service.TransactionService;

@Controller
public class TransactionController
{

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}

	@GetMapping("/transactions")
	public String transactionsPage(Model model, Principal principal)
	{
		String            email        = principal.getName();
		List<Transaction> transactions = transactionService.getTransactionsForUser(email);
		
		List<TransactionDto> transactionsDto = transactions.stream()
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

		model.addAttribute("transactions", transactionsDto);

		return "transactions";
	}
}
