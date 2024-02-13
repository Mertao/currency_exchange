package com.example.currencyexchange.entity;

import java.math.BigDecimal;

public class ExchangeRateRequest {
	private String baseCurrencyCode;
	private String targetCurrencyCode;
	private BigDecimal rate;
	
	public ExchangeRateRequest(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
		super();
		this.baseCurrencyCode = baseCurrencyCode;
		this.targetCurrencyCode = targetCurrencyCode;
		this.rate = rate;
	}
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	public String getTargetCurrencyCode() {
		return targetCurrencyCode;
	}
	public void setTargetCurrencyCode(String targetCurrencyCode) {
		this.targetCurrencyCode = targetCurrencyCode;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
