package com.example.currencyexchange.dto;

import java.math.BigDecimal;

import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.entity.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ExchangeRateDTO {
	private int id;
	private CurrencyDTO baseCurrency;
	private CurrencyDTO targetCurrency;
	private BigDecimal rate;

	public static ExchangeRateDTO toDTO(ExchangeRate exchangeRate) {
		return ExchangeRateDTO.builder().id(exchangeRate.getId())
				.baseCurrency(CurrencyDTO.toDTO(exchangeRate.getBaseCurrency()))
				.targetCurrency(CurrencyDTO.toDTO(exchangeRate.getTargetCurrency()))
				.rate(exchangeRate.getRate())
				.build();
	}

	public static ExchangeRate fromDTO(ExchangeRateDTO exchangeRateDTO) {
		return ExchangeRate.builder().id(exchangeRateDTO.getId())
				.baseCurrency(CurrencyDTO.fromDTO(exchangeRateDTO.getBaseCurrency()))
				.targetCurrency(CurrencyDTO.fromDTO(exchangeRateDTO.getTargetCurrency()))
				.rate(exchangeRateDTO.getRate())
				.build();
	}

	public static ExchangeRate swapCurrencyIfNotRightPlace(ExchangeRate exchangeRate) {
		Currency tempCurrency = exchangeRate.getBaseCurrency();
		exchangeRate.setBaseCurrency(exchangeRate.getTargetCurrency());
		exchangeRate.setTargetCurrency(tempCurrency);
		exchangeRate.setRate(ExchangeRateResponseDTO.getReverseRate(exchangeRate.getRate()));
		return exchangeRate;
	}

	public static boolean baseCurrencyRateRightPlace(ExchangeRate exchangeRate, String baseCurrencyCode) {
		return exchangeRate.getBaseCurrency().getCode().equals(baseCurrencyCode);
	}
}
