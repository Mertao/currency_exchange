package com.example.currencyexchange.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.entity.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExchangeRateResponseDTO {
	private Currency baseCurrency;
	private Currency targetCurrency;
	private BigDecimal rate;
	private BigDecimal amount;
	private BigDecimal convertedAmount;
	
	private static final int NUMBER_AFTER_POINT = 2;
	
	public static ExchangeRateResponseDTO setExchangeRateResponseFromList(List<ExchangeRate> exchangeRates,
			String baseCurrencyCode, BigDecimal amount) {
		exchangeRates = swapUsdPlace(exchangeRates);
		
		if (!targetCurrencyRateRightPlace(exchangeRates, baseCurrencyCode)) {
			exchangeRates = swapExchangeRates(exchangeRates, baseCurrencyCode);
		}

		Currency baseCurrency = exchangeRates.get(0).getTargetCurrency();
		Currency targetCurrency = exchangeRates.get(1).getTargetCurrency();
		
		BigDecimal baseRate = exchangeRates.get(0).getRate();
		BigDecimal targetRate = exchangeRates.get(1).getRate();
		
		// TODO написать во wrapper
		ExchangeRateResponseDTO exchangeRateResponseDTO = ExchangeRateResponseDTO.builder()
				.rate(calculateRate(baseRate, targetRate))
				.baseCurrency(baseCurrency)
				.targetCurrency(targetCurrency)
				.amount(amount)
				.convertedAmount(calculateAmount(baseRate, targetRate, amount))
				.build();
		return exchangeRateResponseDTO;
	}

	public static ExchangeRateResponseDTO setExchangeRateResponse(ExchangeRate exchangeRate, String baseCurrencyCode, BigDecimal amount) {
		if (!baseCurrencyRateRightPlace(exchangeRate, baseCurrencyCode)) {
			BigDecimal rate = getReverseRate(exchangeRate.getRate());
			// TODO написать во wrapper
			return ExchangeRateResponseDTO.builder()
					.rate(rate)
					.baseCurrency(exchangeRate.getTargetCurrency())
					.targetCurrency(exchangeRate.getBaseCurrency())
					.amount(amount)
					.convertedAmount(rate.multiply(amount))
					.build();
		}
		// TODO написать во wrapper
		return ExchangeRateResponseDTO.builder()
				.rate(exchangeRate.getRate())
				.baseCurrency(exchangeRate.getBaseCurrency())
				.targetCurrency(exchangeRate.getTargetCurrency())
				.amount(amount)
				.convertedAmount(exchangeRate.getRate().multiply(amount))
				.build();
	}
	
	public static List<ExchangeRate> swapUsdPlace(List<ExchangeRate> exchangeRates) {
		for (int i = 0; i < exchangeRates.size(); i++) {
			ExchangeRate exchangeRate = exchangeRates.get(i);
			if (!baseCurrencyRateRightPlace(exchangeRate, "USD")) {
				Currency tempCurrency = exchangeRate.getBaseCurrency();
				exchangeRate.setBaseCurrency(exchangeRate.getTargetCurrency());
				exchangeRate.setTargetCurrency(tempCurrency);
				exchangeRate.setRate(getReverseRate(exchangeRate.getRate()));
			}
		}
		return exchangeRates;
	}
	
	public static BigDecimal getReverseRate(BigDecimal rate) {
		return BigDecimal.ONE.divide(rate, NUMBER_AFTER_POINT, RoundingMode.HALF_UP);
	}

	private static List<ExchangeRate> swapExchangeRates(List<ExchangeRate> exchangeRates, String baseCurrencyCode) {
		Collections.swap(exchangeRates, 0, 1);
		return exchangeRates;
	}
	
	private static BigDecimal calculateAmount(BigDecimal baseRate, BigDecimal targetRate, BigDecimal amount) {
		return calculateRate(baseRate, targetRate).multiply(amount);
	}
	
	private static BigDecimal calculateRate(BigDecimal baseRate, BigDecimal targetRate) {
		return targetRate.divide(baseRate, NUMBER_AFTER_POINT, RoundingMode.HALF_UP);
	}
	
	private static boolean targetCurrencyRateRightPlace(List<ExchangeRate> exchangeRates, String baseCurrencyCode) {
		return exchangeRates.get(0).getTargetCurrency().getCode().equals(baseCurrencyCode);
	}
	
	private static boolean baseCurrencyRateRightPlace(ExchangeRate exchangeRate, String baseCurrencyCode) {
		return exchangeRate.getBaseCurrency().getCode().equals(baseCurrencyCode);
	}
}
