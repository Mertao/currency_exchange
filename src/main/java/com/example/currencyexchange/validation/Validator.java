package com.example.currencyexchange.validation;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import com.example.currencyexchange.dto.CurrencyDTO;
import com.example.currencyexchange.dto.ExchangeRateRequestDTO;
import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.exceptions.NotFoundException;
import com.example.currencyexchange.exceptions.RequestException;

public class Validator {
	private static final int CODE_LENGTH = 3;

	public static void currencyRequestFields(CurrencyDTO currencyDTO) {
		currencyCode(currencyDTO.getCode());
		if (emptyField(currencyDTO.getCode()) || emptyField(currencyDTO.getName()) || emptyField(currencyDTO.getSign())) {
			throw new RequestException("Fields cannot be empty");
		}
	}

	public static void currencyCode(String code) {
		String regex = "^[a-zA-Z]{" + CODE_LENGTH + "}$";
		if (code == null || !Pattern.matches(regex, code)) {
			throw new RequestException("The currency code must consist of 3 characters and must not be empty");
		}
	}

	public static void exchangeRateCodes(String codes) {
		String regex = "^[a-zA-Z]{" + CODE_LENGTH * 2 + "}$";
		if (codes == null || !Pattern.matches(regex, codes)) {
			throw new RequestException("The codes are entered incorrectly or are empty");
		}
	}

	public static void exchangeRateRequest(ExchangeRateRequestDTO exchangeRateRequest) {
		if (exchangeRateRequest.getBaseCurrencyCode() == null || exchangeRateRequest.getTargetCurrencyCode() == null
				|| exchangeRateRequest.getRate() == null) {
			throw new RequestException("Fields cannot be empty");
		}
	}

	public static void exchangeRates(List<ExchangeRate> exchangeRates) {
		if (exchangeRates == null || exchangeRates.size() < 2) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}

	}
	
	public static void rate(BigDecimal rate) {
		if (rate == null) {
			throw new RequestException("Fields cannot be empty");
		}
		if (rate.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RequestException("The exchange rate cannot be less than 0");
		}
	}
	
	public static void amountExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
		exchangeRateCodes(baseCurrencyCode + targetCurrencyCode);
		if (amount == null) {
			throw new RequestException("Amount cannot be empty");
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new RequestException("The amount cannot be less than 0");
		}
	}
	
	public static void currencyRequest(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency) {
		if (baseCurrency == null || targetCurrency == null) {
			throw new NotFoundException("One or both currencies are missing from the database");
		}
	}
	
	private static boolean emptyField(String field) {
		return field == null || field.isEmpty() || field.isBlank();
	}
}
