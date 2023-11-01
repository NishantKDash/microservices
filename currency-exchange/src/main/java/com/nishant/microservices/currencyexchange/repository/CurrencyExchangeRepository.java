package com.nishant.microservices.currencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishant.microservices.currencyexchange.models.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long>{

	public CurrencyExchange findByFromAndTo(String from, String to);
}
