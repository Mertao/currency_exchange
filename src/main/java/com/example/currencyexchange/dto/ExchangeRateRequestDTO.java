package com.example.currencyexchange.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data public class ExchangeRateRequestDTO {
	private String baseCurrencyCode;
	private String targetCurrencyCode;
	private BigDecimal rate;
}
