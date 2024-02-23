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
	private String name;
	private String sign;
	
	public static CurrencyDTO toDTO(Currency currency) {
		return CurrencyDTO.builder()
				.id(currency.getId())
				.code(currency.getCode())
				.name(currency.getFullName())
				.sign(currency.getSign())
				.build();
	}
	
	public static Currency fromDTO(CurrencyDTO currencyDTO) {
		return Currency.builder()
				.id(currencyDTO.getId())
				.code(currencyDTO.getCode().toUpperCase())
				.fullName(currencyDTO.getName())
				.sign(currencyDTO.getSign())
				.build();
	}
}
