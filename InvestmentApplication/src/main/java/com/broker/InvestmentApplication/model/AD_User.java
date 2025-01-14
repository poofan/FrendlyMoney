package com.broker.InvestmentApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AD_User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;

	@Column(unique = true, nullable = false)
	private String email;
	private String name;
	private String password;
	private Double balance = 0.0;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Double getBalance()
	{
		return balance;
	}

	public void setBalance(Double balance)
	{
		this.balance = balance;
	}

	public Long getId()
	{
		return id;
	}

}
