package com.example.currencyexchange.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.entity.ExchangeRateRequest;
import com.example.currencyexchange.entity.ExchangeRateResponse;
import com.example.currencyexchange.exceptions.RequestException;

public class ExchangeRateUtil {
	public static ExchangeRateResponse setExchangeRateResponse(ExchangeRateResponse response, ExchangeRate rate1,
			ExchangeRate rate2, BigDecimal amount) {
		if (rate2 == null) {
			response.setRate(rate1.getRate());
			response.setBaseCurrency(rate1.getBaseCurrency());
			response.setTargetCurrency(rate1.getTargetCurrency());
			response.setAmount(amount);
			response.setConvertedAmount(rate1.getRate().multiply(amount));
		} else {
			response.setRate(rate1.getRate().divide(rate2.getRate(), 2, RoundingMode.HALF_UP));
			response.setBaseCurrency(rate1.getTargetCurrency());
			response.setTargetCurrency(rate2.getTargetCurrency());
			response.setAmount(amount);
			response.setConvertedAmount(rate1.getRate().divide(rate2.getRate(), 2, RoundingMode.HALF_UP).multiply(amount));
		}
		return response;
	}
	
	public static boolean CheckExchangeRateRequest(ExchangeRateRequest exchangeRateRequest) {
		return exchangeRateRequest.getBaseCurrencyCode() != null && exchangeRateRequest.getTargetCurrencyCode() != null && exchangeRateRequest.getRate() != null;
	}
}
