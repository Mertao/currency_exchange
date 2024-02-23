package com.example.currencyexchange.service;

import java.math.BigDecimal;
import java.util.Arrays;
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
import com.example.currencyexchange.exceptions.NotFoundException;
import com.example.currencyexchange.validation.Validator;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	CurrencyService currencyService;
	
	private static final int CODE_LENGTH = 3;

	@Override
	public List<ExchangeRateDTO> getExchangeRateList() {
		return exchangeRateRepository.findAll().stream().map(ExchangeRateDTO::toDTO).collect(Collectors.toList());
	}

	@Override
	public ExchangeRateDTO getExchangeRateByCodes(String codes) {
		Validator.exchangeRateCodes(codes);
		String baseCurrencyCode = codes.substring(0, CODE_LENGTH);
		String targetCurrencyCode = codes.substring(CODE_LENGTH);
		ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCodes(baseCurrencyCode, targetCurrencyCode);
		if (exchangeRate == null) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}
		if (!ExchangeRateDTO.baseCurrencyRateRightPlace(exchangeRate, baseCurrencyCode)) {
			exchangeRate = ExchangeRateDTO.swapCurrencyIfNotRightPlace(exchangeRate);
		}
		return ExchangeRateDTO.toDTO(exchangeRate);
	}

	@Override
	public ExchangeRateDTO saveExchangeRate(ExchangeRateRequestDTO exchangeRateRequest) {
		CurrencyDTO baseCurrency = currencyService.getCurrencyByCode(exchangeRateRequest.getBaseCurrencyCode());
		CurrencyDTO targetCurrency = currencyService.getCurrencyByCode(exchangeRateRequest.getTargetCurrencyCode());		
		Validator.currencyRequest(baseCurrency, targetCurrency);
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
		String baseCurrencyCode = codes.substring(0, CODE_LENGTH);
		String targetCurrencyCode = codes.substring(CODE_LENGTH);
		Validator.rate(rate);
		ExchangeRate exchangeRate = ExchangeRateDTO.fromDTO(getExchangeRateByCodes(baseCurrencyCode + targetCurrencyCode));
		if (!ExchangeRateDTO.baseCurrencyRateRightPlace(exchangeRate, baseCurrencyCode)) {
			exchangeRate = ExchangeRateDTO.swapCurrencyIfNotRightPlace(exchangeRate);
		}
		exchangeRate.setRate(rate);
		return ExchangeRateDTO.toDTO(exchangeRateRepository.save(exchangeRate));
	}

	@Override
	public ExchangeRateResponseDTO getAmountExchangeRate(String baseCurrencyCode, String targetCurrencyCode,
			BigDecimal amount) {
		Validator.amountExchangeRate(baseCurrencyCode, targetCurrencyCode, amount);
		ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCodes(baseCurrencyCode,
				targetCurrencyCode);
		if (exchangeRate == null) {
			return getAmountExchangeRateByUsd(baseCurrencyCode, targetCurrencyCode, amount);
		}
		return ExchangeRateResponseDTO.setExchangeRateResponse(exchangeRate, baseCurrencyCode, amount);
	}

	private ExchangeRateResponseDTO getAmountExchangeRateByUsd(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findUsdExchangeRateByCurrencyCodes(Arrays.asList(baseCurrencyCode, targetCurrencyCode));
		Validator.exchangeRates(exchangeRates);
		return ExchangeRateResponseDTO.setExchangeRateResponseFromList(exchangeRates, baseCurrencyCode, amount);
	}
}
