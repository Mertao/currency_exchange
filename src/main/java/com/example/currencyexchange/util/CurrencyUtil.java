package com.example.currencyexchange.util;

import com.example.currencyexchange.entity.Currency;

public class CurrencyUtil {
	public static boolean CheckCurrencyFields(Currency currency) {
		return currency.getCode() != null && currency.getFullName() != null && currency.getSign() != null;
	}
}
