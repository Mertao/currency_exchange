package com.example.currencyexchange.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "base_currency_id", referencedColumnName = "id")
	private Currency baseCurrency;
	
	@ManyToOne
	@JoinColumn(name = "target_currency_id", referencedColumnName = "id")
	private Currency targetCurrency;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
	public ExchangeRate() {}

	public ExchangeRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
		this.baseCurrency = baseCurrency;
		this.targetCurrency = targetCurrency;
		this.rate = rate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Currency getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "ExchangeRate [id=" + id + ", baseCurrencyId=" + baseCurrency + ", targetCurrencyId="
				+ targetCurrency + ", rate=" + rate + "]";
	}
	
	
}
