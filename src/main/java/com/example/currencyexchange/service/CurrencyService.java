package com.example.currencyexchange.service;

import java.util.List;

import com.example.currencyexchange.dto.CurrencyDTO;

public interface CurrencyService {
	public List<CurrencyDTO> getCurrencyList();
	public CurrencyDTO getCurrencyByCode(String code);
	public CurrencyDTO saveCurrency(CurrencyDTO currencyRequestDTO);
}
