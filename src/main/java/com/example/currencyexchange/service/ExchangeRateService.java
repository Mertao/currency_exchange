package com.example.currencyexchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.currencyexchange.dao.ExchangeRateRepository;
import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.entity.ExchangeRateRequest;
import com.example.currencyexchange.entity.ExchangeRateResponse;

public interface ExchangeRateService {
	Optional<List<ExchangeRate>> getExchangeRateList();
	Optional<ExchangeRate> getExchangeRateByCodes(String codes);
	Optional<ExchangeRate> saveExchangeRate(ExchangeRateRequest exchangeRateRequest);
	Optional<ExchangeRate> updateExchangeRate(ExchangeRateRequest exchangeRateRequest, String codes);
	Optional<ExchangeRateResponse> getAmountExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount);
}
