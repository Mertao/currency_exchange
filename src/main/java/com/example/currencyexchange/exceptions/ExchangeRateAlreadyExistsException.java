package com.example.currencyexchange.exceptions;

@SuppressWarnings("serial")
public class ExchangeRateAlreadyExistsException extends RuntimeException{
	public ExchangeRateAlreadyExistsException(String message) {
		super(message);
	}
}
