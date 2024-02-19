package com.example.currencyexchange.dto;

import java.math.BigDecimal;

import com.example.currencyexchange.entity.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data public class ExchangeRateDTO {
	private int id;
	private CurrencyDTO baseCurrency;
	private CurrencyDTO targetCurrency;
	private BigDecimal rate;
	
	public static ExchangeRateDTO toDTO(ExchangeRate exchangeRate) {
		return ExchangeRateDTO.builder()
				.id(exchangeRate.getId())
				.baseCurrency(CurrencyDTO.toDTO(exchangeRate.getBaseCurrency()))
				.targetCurrency(CurrencyDTO.toDTO(exchangeRate.getTargetCurrency()))
				.rate(exchangeRate.getRate())
				.build();
	}
	
	public static ExchangeRate fromDTO(ExchangeRateDTO exchangeRateDTO) {
		return ExchangeRate.builder()
                .id(exchangeRateDTO.getId())
                .baseCurrency(CurrencyDTO.fromDTO(exchangeRateDTO.getBaseCurrency()))
                .targetCurrency(CurrencyDTO.fromDTO(exchangeRateDTO.getTargetCurrency()))
                .rate(exchangeRateDTO.getRate())
                .build();
	}
}
