package com.example.currencyexchange.service;

import java.util.List;
import java.util.Optional;

import com.example.currencyexchange.entity.Currency;

public interface CurrencyService {
	public Optional<List<Currency>> getCurrencyList();
	public Optional<Currency> getCurrencyByCode(String code);
	public Optional<Currency> saveCurrency(Currency currency);
}
