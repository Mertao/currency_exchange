package com.example.currencyexchange.exception_handling;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.currencyexchange.exceptions.CurrencyAlreadyExistsException;
import com.example.currencyexchange.exceptions.RequestException;
import com.example.currencyexchange.exceptions.ExceptionInfo;
import com.example.currencyexchange.exceptions.ExchangeRateAlreadyExistsException;
import com.example.currencyexchange.exceptions.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CurrencyAlreadyExistsException.class)
	public ResponseEntity<ExceptionInfo> handleException(CurrencyAlreadyExistsException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo(exception.getMessage());

		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RequestException.class)
	public ResponseEntity<ExceptionInfo> handleException(RequestException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo(exception.getMessage());

		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionInfo> handleException(NotFoundException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo(exception.getMessage());

		return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ExceptionInfo> handleException(DataAccessException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo(exception.getMessage());

		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionInfo> handleException(DataIntegrityViolationException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo("The field cannot be null");

		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ExchangeRateAlreadyExistsException.class)
	public ResponseEntity<ExceptionInfo> handleException(ExchangeRateAlreadyExistsException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo(exception.getMessage());

		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionInfo> handleException(RuntimeException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo("Internal Server Error");

		return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ExceptionInfo> handleException(NoResourceFoundException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo("The resource was not found");

		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionInfo> handleException(HttpRequestMethodNotSupportedException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo("Method Not Allowed");
		return new ResponseEntity<>(info, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ExceptionInfo> handleException(MissingServletRequestParameterException exception) {
		ExceptionInfo info = new ExceptionInfo();
		info.setInfo("Required parameter is missing");

		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}
}
