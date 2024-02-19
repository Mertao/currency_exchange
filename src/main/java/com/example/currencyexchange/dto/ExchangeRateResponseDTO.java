package com.example.currencyexchange.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	public static ExchangeRateResponseDTO setExchangeRateResponse(List<ExchangeRate> exchangeRates,
			String baseCurrencyCode, BigDecimal amount) {
		exchangeRates = swapIfCodeNamesNotEquals(exchangeRates, baseCurrencyCode);
		Currency baseCurrency = exchangeRates.get(0).getTargetCurrency();
		Currency targetCurrency = exchangeRates.get(1).getTargetCurrency();
		
		BigDecimal baseRate = exchangeRates.get(0).getRate();
		BigDecimal targetRate = exchangeRates.get(1).getRate();

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
		if (!exchangeRate.getBaseCurrency().getCode().equals(baseCurrencyCode)) {
			BigDecimal rate = getReverseRate(exchangeRate.getRate());
			return ExchangeRateResponseDTO.builder()
					.rate(rate)
					.baseCurrency(exchangeRate.getTargetCurrency())
					.targetCurrency(exchangeRate.getBaseCurrency())
					.amount(amount)
					.convertedAmount(rate.multiply(amount))
					.build();
		}
		return ExchangeRateResponseDTO.builder()
				.rate(exchangeRate.getRate())
				.baseCurrency(exchangeRate.getBaseCurrency())
				.targetCurrency(exchangeRate.getTargetCurrency())
				.amount(amount)
				.convertedAmount(exchangeRate.getRate().multiply(amount))
				.build();
	}

	private static List<ExchangeRate> swapIfCodeNamesNotEquals(List<ExchangeRate> exchangeRates, String baseCurrencyCode) {
		if (!exchangeRates.get(0).getTargetCurrency().getCode().equals(baseCurrencyCode)) {
			Collections.swap(exchangeRates, 0, 1);
		}
		return exchangeRates;
	}

	private static BigDecimal calculateRate(BigDecimal baseRate, BigDecimal targetRate) {
		return baseRate.divide(targetRate, 2, RoundingMode.HALF_UP);
	}
	
	private static BigDecimal calculateAmount(BigDecimal baseRate, BigDecimal targetRate, BigDecimal amount) {
		return calculateRate(baseRate, targetRate).multiply(amount);
	}
	
	private static BigDecimal getReverseRate(BigDecimal rate) {
		return BigDecimal.ONE.divide(rate, 2, RoundingMode.HALF_UP);
	}
}
