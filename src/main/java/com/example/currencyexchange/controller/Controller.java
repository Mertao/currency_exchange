package com.example.currencyexchange.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.entity.CurrencyRequest;
import com.example.currencyexchange.entity.ExchangeRate;
import com.example.currencyexchange.entity.ExchangeRateRequest;
import com.example.currencyexchange.entity.ExchangeRateResponse;
import com.example.currencyexchange.exceptions.NotFoundException;
import com.example.currencyexchange.service.CurrencyService;
import com.example.currencyexchange.service.ExchangeRateService;

@RestController
public class Controller {
	
	@Autowired
	CurrencyService currencyService;
	@Autowired
	ExchangeRateService exchangeRateService;
	
	@GetMapping("/currencies")
	public ResponseEntity<Optional<List<Currency>>> getAllCurrency() {
		return new ResponseEntity<>(currencyService.getCurrencyList(), HttpStatus.OK);
	}

	@GetMapping("/currency/{code}")
	public ResponseEntity<Optional<Currency>> getCurrencyByCode(@PathVariable("code") String code) {
		Optional<Currency> currency = currencyService.getCurrencyByCode(code);
		if (currency.isEmpty()) {
			throw new NotFoundException("The currency could not be found");
		}
		return new ResponseEntity<>(currency, HttpStatus.OK);
	}

	@PostMapping("/currencies")
	public ResponseEntity<Optional<Currency>> saveCurrency(@RequestBody CurrencyRequest currencyDto) {
		Currency currency = new Currency();
		currency.setFullName(currencyDto.getName());
		currency.setCode(currencyDto.getCode());
		currency.setSign(currencyDto.getSign());
		return new ResponseEntity<>(currencyService.saveCurrency(currency), HttpStatus.OK);
	}
	
	@GetMapping("/exchangeRates")
	public ResponseEntity<Optional<List<ExchangeRate>>> getAllExchangeRates() {
		return new ResponseEntity<>(exchangeRateService.getExchangeRateList(), HttpStatus.OK);
	}
	
	@GetMapping("/exchangeRate/{codes}")
	public ResponseEntity<Optional<ExchangeRate>> getExchangeRateByCodes(@PathVariable("codes") String codes) {
		Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByCodes(codes);
		if (exchangeRate.isEmpty()) {
			throw new NotFoundException("The exchange rate could not be found");
		}
		return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
	}

	@PostMapping("/exchangeRates")
	public ResponseEntity<Optional<ExchangeRate>> saveExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		return new ResponseEntity<>(exchangeRateService.saveExchangeRate(exchangeRateRequest), HttpStatus.OK);
	}

	@PatchMapping("/exchangeRate/{codes}")
	public ResponseEntity<Optional<ExchangeRate>> updateExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest, @PathVariable("codes") String codes) {
		return new ResponseEntity<>(exchangeRateService.updateExchangeRate(exchangeRateRequest, codes), HttpStatus.OK);
	}
	
	@GetMapping("/exchange")
	public ResponseEntity<Optional<ExchangeRateResponse>>  getAmountExchangeRate(@RequestParam("from") String baseCurrencyCode, 
			@RequestParam("to") String targetCurrencyCode, @RequestParam("amount") BigDecimal amount) {
		return new ResponseEntity<>(exchangeRateService.getAmountExchangeRate(baseCurrencyCode, targetCurrencyCode, amount), HttpStatus.OK);
	}
	
}
