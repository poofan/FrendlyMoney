package com.broker.InvestmentApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.broker.InvestmentApplication.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
	List<Transaction> findByEmail(String email);

}
