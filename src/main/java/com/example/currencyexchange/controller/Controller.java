package com.example.currencyexchange.controller;

import java.math.BigDecimal;
import java.util.List;

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

import com.example.currencyexchange.dto.CurrencyDTO;
import com.example.currencyexchange.dto.CurrencyRequestDTO;
import com.example.currencyexchange.dto.ExchangeRateDTO;
import com.example.currencyexchange.dto.ExchangeRateRequestDTO;
import com.example.currencyexchange.dto.ExchangeRateResponseDTO;
import com.example.currencyexchange.service.CurrencyService;
import com.example.currencyexchange.service.ExchangeRateService;

@RestController
public class Controller {

	@Autowired
	CurrencyService currencyService;
	@Autowired
	ExchangeRateService exchangeRateService;

	@GetMapping("/currency")
	public ResponseEntity<List<CurrencyDTO>> getAllCurrency() {
		return new ResponseEntity<>(currencyService.getCurrencyList(), HttpStatus.OK);
	}

	@GetMapping("/currency/{code}")
	public ResponseEntity<CurrencyDTO> getCurrencyByCode(@PathVariable("code") String currencyCode) {
		return new ResponseEntity<>(currencyService.getCurrencyByCode(currencyCode.toUpperCase()), HttpStatus.OK);
	}

	@PostMapping("/currency")
	public ResponseEntity<CurrencyDTO> saveCurrency(@RequestBody CurrencyRequestDTO currencyRequestDTO) {
		return new ResponseEntity<>(currencyService.saveCurrency(currencyRequestDTO), HttpStatus.OK);
	}

	@GetMapping("/exchangeRate")
	public ResponseEntity<List<ExchangeRateDTO>> getAllExchangeRates() {
		return new ResponseEntity<>(exchangeRateService.getExchangeRateList(), HttpStatus.OK);
	}

	@GetMapping("/exchangeRate/{codes}")
	public ResponseEntity<ExchangeRateDTO> getExchangeRateByCodes(@PathVariable("codes") String currencyCodes) {
		return new ResponseEntity<>(exchangeRateService.getExchangeRateByCodes(currencyCodes.toUpperCase()),
				HttpStatus.OK);
	}
	
	@PostMapping("/exchangeRate")
	public ResponseEntity<ExchangeRateDTO> saveExchangeRate(
			@RequestBody ExchangeRateRequestDTO ExchangeRateRequestDTO) {
		return new ResponseEntity<>(exchangeRateService.saveExchangeRate(ExchangeRateRequestDTO), HttpStatus.OK);
	}

	@PatchMapping("/exchangeRate/{codes}")
	public ResponseEntity<ExchangeRateDTO> updateExchangeRate(
			@RequestBody ExchangeRateRequestDTO exchangeRateRequestDTO, @PathVariable("codes") String currencyCodes) {
		return new ResponseEntity<>(
				exchangeRateService.updateExchangeRate(exchangeRateRequestDTO, currencyCodes.toUpperCase()),
				HttpStatus.OK);
	}

	@GetMapping("/exchange")
	public ResponseEntity<ExchangeRateResponseDTO> getAmountExchangeRate(@RequestParam("from") String baseCurrencyCode,
			@RequestParam("to") String targetCurrencyCode, @RequestParam("amount") BigDecimal currencyAmount) {
		return new ResponseEntity<>(exchangeRateService.getAmountExchangeRate(baseCurrencyCode.toUpperCase(),
				targetCurrencyCode.toUpperCase(), currencyAmount), HttpStatus.OK);
	}

}
