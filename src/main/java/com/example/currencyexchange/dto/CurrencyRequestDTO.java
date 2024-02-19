package com.example.currencyexchange.dto;

import com.example.currencyexchange.entity.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data public class CurrencyRequestDTO {
	private String name;
	private String code;
	private String sign;
	
	public static CurrencyRequestDTO toDTO(Currency currency) {
		return CurrencyRequestDTO.builder()
                .name(currency.getFullName())
                .code(currency.getCode())
                .sign(currency.getSign())
                .build();
	}
	
	public static Currency fromDTO(CurrencyRequestDTO currencyRequestDTO) {
		return Currency.builder()
                .fullName(currencyRequestDTO.getName())
                .code(currencyRequestDTO.getCode())
                .sign(currencyRequestDTO.getSign())
                .build();
	}
}
