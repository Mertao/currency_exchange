package com.example.currencyexchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.currencyexchange.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer>{
	Currency findByCode(String code);
	
}
