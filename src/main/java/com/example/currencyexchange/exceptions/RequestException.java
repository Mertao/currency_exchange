package com.example.currencyexchange.exceptions;

@SuppressWarnings("serial")
public class RequestException extends RuntimeException{
	public RequestException(String message) {
		super(message);
	}
}
