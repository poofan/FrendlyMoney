package com.broker.InvestmentApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.broker.InvestmentApplication.model.AD_User;

public interface UserRepository extends JpaRepository<AD_User, Long>
{
	Optional<AD_User> findByEmail(String email);
}
