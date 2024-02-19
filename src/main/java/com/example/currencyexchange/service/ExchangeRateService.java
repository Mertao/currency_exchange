package com.example.currencyexchange.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.currencyexchange.dto.ExchangeRateDTO;
import com.example.currencyexchange.dto.ExchangeRateRequestDTO;
import com.example.currencyexchange.dto.ExchangeRateResponseDTO;

public interface ExchangeRateService {
	List<ExchangeRateDTO> getExchangeRateList();
	ExchangeRateDTO getExchangeRateByCodes(String codes);
	ExchangeRateDTO saveExchangeRate(ExchangeRateRequestDTO exchangeRateRequest);
	ExchangeRateDTO updateExchangeRate(ExchangeRateRequestDTO exchangeRateRequest, String codes);
	ExchangeRateResponseDTO getAmountExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount);
}
