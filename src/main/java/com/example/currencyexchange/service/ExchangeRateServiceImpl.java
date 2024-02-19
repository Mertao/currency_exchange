package com.example.currencyexchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.currencyexchange.dao.ExchangeRateRepository;
import com.example.currencyexchange.dto.CurrencyDTO;
import com.example.currencyexchange.dto.ExchangeRateDTO;
import com.example.currencyexchange.dto.ExchangeRateRequestDTO;
import com.example.currencyexchange.dto.ExchangeRateResponseDTO;
import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.exceptions.ExchangeRateAlreadyExistsException;
import com.example.currencyexchange.exceptions.NotFoundException;
import com.example.currencyexchange.validation.Validation;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	CurrencyService currencyService;

	@Override
	public List<ExchangeRateDTO> getExchangeRateList() {
		return exchangeRateRepository.findAll().stream().map(ExchangeRateDTO::toDTO).collect(Collectors.toList());
	}

	@Override
	public ExchangeRateDTO getExchangeRateByCodes(String codes) {
		Validation.exchangeRateCodesValidation(codes);
		ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCodes(codes.substring(0, 3),
				codes.substring(3));
		if (exchangeRate == null) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}
		return ExchangeRateDTO.toDTO(exchangeRate);
	}

	@Override
	public ExchangeRateDTO saveExchangeRate(ExchangeRateRequestDTO exchangeRateRequest) {
		CurrencyDTO baseCurrency = currencyService.getCurrencyByCode(exchangeRateRequest.getBaseCurrencyCode());
		CurrencyDTO targetCurrency = currencyService.getCurrencyByCode(exchangeRateRequest.getTargetCurrencyCode());
		Validation.currencyRequestValidation(baseCurrency, targetCurrency);
		if (exchangeRateRepository.findExchangeRateByCodes(baseCurrency.getCode(), targetCurrency.getCode()) != null) {
			throw new ExchangeRateAlreadyExistsException("Exchange rate already exists in the database");
		}
		ExchangeRate exchangeRate = exchangeRateRepository.save(ExchangeRate.builder()
				.baseCurrency(CurrencyDTO.fromDTO(baseCurrency))
				.targetCurrency(CurrencyDTO.fromDTO(targetCurrency))
				.rate(exchangeRateRequest.getRate())
				.build());
		return ExchangeRateDTO.toDTO(exchangeRate);
	}

	@Override
	public ExchangeRateDTO updateExchangeRate(ExchangeRateRequestDTO exchangeRateRequest, String codes) {
		BigDecimal rate = exchangeRateRequest.getRate();
		Validation.rateValidation(rate);
		ExchangeRate exchangeRate = ExchangeRateDTO.fromDTO(getExchangeRateByCodes(codes));
		exchangeRate.setRate(rate);
		return ExchangeRateDTO.toDTO(exchangeRateRepository.save(exchangeRate));
	}

	@Override
	public ExchangeRateResponseDTO getAmountExchangeRate(String baseCurrencyCode, String targetCurrencyCode,
			BigDecimal amount) {
		Validation.amountExchangeRateValidation(baseCurrencyCode, targetCurrencyCode, amount);
		ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCodes(baseCurrencyCode,
				targetCurrencyCode);
		if (exchangeRate == null) {
			return getAmountExchangeRateByUsd(baseCurrencyCode, targetCurrencyCode, amount);
		}
		return ExchangeRateResponseDTO.setExchangeRateResponse(exchangeRate, baseCurrencyCode, amount);
	}

	private ExchangeRateResponseDTO getAmountExchangeRateByUsd(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
			List<ExchangeRate> exchangeRates = exchangeRateRepository.findUsdExchangeRateByCodes(baseCurrencyCode, targetCurrencyCode);
			Validation.exchangeRatesValidation(exchangeRates);
			return ExchangeRateResponseDTO.setExchangeRateResponse(exchangeRates, baseCurrencyCode, amount);
	}
}
