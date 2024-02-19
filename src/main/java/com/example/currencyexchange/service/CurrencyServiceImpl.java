package com.example.currencyexchange.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.currencyexchange.dao.CurrencyRepository;
import com.example.currencyexchange.dto.CurrencyDTO;
import com.example.currencyexchange.dto.CurrencyRequestDTO;
import com.example.currencyexchange.entity.Currency;
import com.example.currencyexchange.exceptions.CurrencyAlreadyExistsException;
import com.example.currencyexchange.exceptions.RequestException;
import com.example.currencyexchange.validation.Validation;

@Service
public class CurrencyServiceImpl implements CurrencyService{
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public List<CurrencyDTO> getCurrencyList() {
		return currencyRepository.findAll().stream().map(CurrencyDTO::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public CurrencyDTO getCurrencyByCode(String code) {
		Validation.currencyCodeValidation(code);
		Currency currency = currencyRepository.findByCode(code.toUpperCase());
		if (currency == null) {
			throw new RequestException("Currency with code " + code.toUpperCase() + " not exists in db.");
		}
		return CurrencyDTO.toDTO(currency);
	}
	
	@Override
	public CurrencyDTO saveCurrency(CurrencyRequestDTO currencyRequestDTO) {
		Validation.currencyFieldsValidation(CurrencyDTO.toDTO(CurrencyRequestDTO.fromDTO(currencyRequestDTO)));	
		Currency existingCurrency = currencyRepository.findByCode(currencyRequestDTO.getCode());
		if (existingCurrency != null) {
			throw new CurrencyAlreadyExistsException("Currency with code " + currencyRequestDTO.getCode() + " already exists in db.");
		}
		return CurrencyDTO.toDTO(currencyRepository.save(CurrencyRequestDTO.fromDTO(currencyRequestDTO)));
	}
}
