package com.example.currencyexchange.dto;

import com.example.currencyexchange.entity.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data public class CurrencyDTO {
	private int id;
	private String code;
	private String fullName;
	private String sign;
	
	public static CurrencyDTO toDTO(Currency currency) {
		return CurrencyDTO.builder()
				.id(currency.getId())
				.code(currency.getCode())
				.fullName(currency.getFullName())
				.sign(currency.getSign())
				.build();
	}
	
	public static Currency fromDTO(CurrencyDTO currencyDTO) {
		return Currency.builder()
				.id(currencyDTO.getId())
				.code(currencyDTO.getCode().toUpperCase())
				.fullName(currencyDTO.getFullName())
				.sign(currencyDTO.getSign())
				.build();
	}
}
