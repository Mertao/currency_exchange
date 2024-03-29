package com.example.currencyexchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.currencyexchange.entity.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
	@Query("SELECT er FROM ExchangeRate er "
            + "JOIN er.baseCurrency bc "
            + "JOIN er.targetCurrency tc "
            + "WHERE bc.code IN (:baseCurrencyCode, :targetCurrencyCode) AND tc.code IN (:baseCurrencyCode, :targetCurrencyCode)")
	public ExchangeRate findExchangeRateByCodes(@Param("baseCurrencyCode") String baseCurrencyCode,
			@Param("targetCurrencyCode") String targetCurrencyCode);

	@Query("SELECT er FROM ExchangeRate er " +
		       "JOIN er.baseCurrency bc " +
		       "JOIN er.targetCurrency tc " +
		       "WHERE (bc.code = 'USD' AND tc.code IN :currencyCodes) " +
		       "OR (bc.code IN :currencyCodes AND tc.code = 'USD')")
	public List<ExchangeRate> findCrossRateByUsd(@Param("currencyCodes") List<String> currencyCodes);
}
