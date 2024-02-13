package com.example.currencyexchange.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.currencyexchange.dao.CurrencyRepository;
import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.exceptions.CurrencyAlreadyExistsException;
import com.example.currencyexchange.exceptions.RequestException;
import com.example.currencyexchange.util.CurrencyUtil;
import com.example.currencyexchange.util.Validation;

@Service
public class CurrencyServiceImpl implements CurrencyService{
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public Optional<List<Currency>> getCurrencyList() {
		List<Currency> currencyList = currencyRepository.findAll();
		return Optional.ofNullable(currencyList);
	}
	
	@Override
	public Optional<Currency> getCurrencyByCode(String code) {
		if (!Validation.currencyCodeValidation(code)) {
			throw new RequestException("The currency code must consist of 3 characters and must not be empty");
		}
		Currency currency = currencyRepository.findByCode(code.toUpperCase());
		
		return Optional.ofNullable(currency);
	}
	
	@Override
	public Optional<Currency> saveCurrency(Currency currency) {
		if (!CurrencyUtil.CheckCurrencyFields(currency)) {
			throw new RequestException("Fields cannot be empty");
		}
		Optional<Currency> existingCurrency = getCurrencyByCode(currency.getCode());
		if (existingCurrency.isPresent()) {
			throw new CurrencyAlreadyExistsException("Currency with code " + currency.getCode() + " already exists in db.");
		}
		
		Currency savedCurrency = currencyRepository.save(currency);
		return Optional.ofNullable(savedCurrency);
	}
}
