package com.broker.InvestmentApplication.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.broker.InvestmentApplication.model.Transaction;
import com.broker.InvestmentApplication.repository.TransactionRepository;

@Service
public class TransactionService
{
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository)
	{
		this.transactionRepository = transactionRepository;
	}

	public List<Transaction> getTransactionsForUser(String email)
	{
		return transactionRepository.findByEmail(email);
	}

	public void saveTransaction(String email, Double amount, String type)
	{
		Transaction transaction = new Transaction();
		
		transaction.setEmail(email);
		transaction.setAmount(amount);
		transaction.setTimestamp(LocalDateTime.now());
		transaction.setType(type);
		
		transactionRepository.save(transaction);
	}
}
