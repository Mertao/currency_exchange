package com.example.currencyexchange.exceptions;

@SuppressWarnings("serial")
public class CurrencyAlreadyExistsException extends RuntimeException{
	public CurrencyAlreadyExistsException(String message) {
		super(message);
	}
}
