package com.example.currencyexchange.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.currencyexchange.entity.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
	@Query("SELECT er FROM ExchangeRate er "
            + "JOIN er.baseCurrency bc "
            + "JOIN er.targetCurrency tc "
            + "WHERE bc.code = :baseCurrencyCode AND tc.code = :targetCurrencyCode")
	public ExchangeRate findExchangeRateByCodes(@Param("baseCurrencyCode") String baseCurrencyCode,
			@Param("targetCurrencyCode") String targetCurrencyCode);
	
	@Query("SELECT er FROM ExchangeRate er "
			+ "JOIN er.baseCurrency bc "
			+ "JOIN er.targetCurrency tc "
			+ "WHERE bc.code = 'USD' AND (tc.code = :baseCurrencyCode OR tc.code = :targetCurrencyCode)")
	public List<ExchangeRate> findUsdExchangeRateByCodes(@Param("baseCurrencyCode") String baseCurrencyCode, @Param("targetCurrencyCode") String TargetCurrencyCode);
}
