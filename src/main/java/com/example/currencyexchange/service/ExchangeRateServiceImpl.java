package com.example.currencyexchange.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.currencyexchange.dao.ExchangeRateRepository;
import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.entity.ExchangeRateRequest;
import com.example.currencyexchange.entity.ExchangeRateResponse;
import com.example.currencyexchange.exceptions.RequestException;
import com.example.currencyexchange.exceptions.ExchangeRateAlreadyExistsException;
import com.example.currencyexchange.exceptions.NotFoundException;
import com.example.currencyexchange.util.ExchangeRateUtil;
import com.example.currencyexchange.util.Validation;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	CurrencyService currencyService;

	@Override
	public Optional<List<ExchangeRate>> getExchangeRateList() {
		List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
		return Optional.ofNullable(exchangeRateList);
	}

	@Override
	public Optional<ExchangeRate> getExchangeRateByCodes(String codes) {
		if (!Validation.exchangeRateCodesValidation(codes)) {
			throw new RequestException("The codes are entered incorrectly or are empty");
		}
		String baseCurrencyCode = codes.substring(0, 3);
		String targetCurrencyCode = codes.substring(3);
		ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCodes(baseCurrencyCode,
				targetCurrencyCode);

		return Optional.ofNullable(exchangeRate);
	}

	@Override
	public Optional<ExchangeRate> saveExchangeRate(ExchangeRateRequest exchangeRateRequest) {
		ExchangeRate exchangeRate = new ExchangeRate();
		if (!ExchangeRateUtil.CheckExchangeRateRequest(exchangeRateRequest)) {
			throw new RequestException("Fields cannot be empty");
		}
		Optional<Currency> baseCurrency = currencyService.getCurrencyByCode(exchangeRateRequest.getBaseCurrencyCode());
		Optional<Currency> targetCurrency = currencyService
				.getCurrencyByCode(exchangeRateRequest.getTargetCurrencyCode());

		if (baseCurrency.isEmpty() || targetCurrency.isEmpty()) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}
		if (getExchangeRateByCodes(baseCurrency.get().getCode() + targetCurrency.get().getCode()).isPresent()) {
			throw new ExchangeRateAlreadyExistsException("Exchange rate already exists in the database");
		}
		
		exchangeRate.setBaseCurrency(baseCurrency.get());
		exchangeRate.setTargetCurrency(targetCurrency.get());
		exchangeRate.setRate(exchangeRateRequest.getRate());
		return Optional.ofNullable(exchangeRateRepository.save(exchangeRate));
	}

	@Override
	public Optional<ExchangeRate> updateExchangeRate(ExchangeRateRequest exchangeRateRequest, String codes) {
		BigDecimal rate = exchangeRateRequest.getRate();
		if (rate == null) {
			throw new RequestException("Fields cannot be empty");
		}
		Optional<ExchangeRate> exchangeRate = getExchangeRateByCodes(codes);
		if (exchangeRate.isEmpty()) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}
		
		if (rate.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RequestException("The exchange rate cannot be less than 0");
		}
		exchangeRate.get().setRate(rate);
		return Optional.ofNullable(exchangeRateRepository.save(exchangeRate.get()));
	}

	@Override
	public Optional<ExchangeRateResponse> getAmountExchangeRate(String baseCurrencyCode, String targetCurrencyCode,
			BigDecimal amount) {
		ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
		if (amount == null) {
			throw new RequestException("Amount cannot be empty");
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new RequestException("The amount cannot be less than 0");
		}
		Optional<ExchangeRate> exchangeRate = getExchangeRateByCodes(baseCurrencyCode + targetCurrencyCode);
		if (exchangeRate.isEmpty()) {
			exchangeRate = getExchangeRateByCodes(targetCurrencyCode + baseCurrencyCode);
			if (exchangeRate.isEmpty()) {
				List<ExchangeRate> exchangeRates = exchangeRateRepository.findUsdExchangeRateByCodes(baseCurrencyCode,
						targetCurrencyCode);
				if (exchangeRates == null || exchangeRates.size() < 2) {
					throw new RequestException("One or both currencies are missing from the database");
				}
				if (!exchangeRates.get(0).getTargetCurrency().getCode().equals(baseCurrencyCode)) {
					Collections.swap(exchangeRates, 0, 1);
				}
				ExchangeRateUtil.setExchangeRateResponse(exchangeRateResponse, exchangeRates.get(0),
						exchangeRates.get(1), amount);
			} else {
			ExchangeRateUtil.setExchangeRateResponse(exchangeRateResponse, exchangeRate.get(), null, amount);
			}
		} else {
			ExchangeRateUtil.setExchangeRateResponse(exchangeRateResponse, exchangeRate.get(), null, amount);
		}
		
		return Optional.of(exchangeRateResponse);
	}

}
