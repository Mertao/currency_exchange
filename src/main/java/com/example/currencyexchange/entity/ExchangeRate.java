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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_rates", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "base_currency_id", "target_currency_id" }) })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ExchangeRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "base_currency_id", referencedColumnName = "id", nullable = false)
	private Currency baseCurrency;

	@ManyToOne
	@JoinColumn(name = "target_currency_id", referencedColumnName = "id", nullable = false)
	private Currency targetCurrency;

	@Column(name = "rate", nullable = false, columnDefinition = "numeric(38,2)")
	private BigDecimal rate;
}
