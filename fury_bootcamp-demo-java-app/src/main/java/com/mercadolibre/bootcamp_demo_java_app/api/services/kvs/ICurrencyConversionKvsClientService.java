package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyConversionDTO;
import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyEnum;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.exceptions.KvsException;

public interface ICurrencyConversionKvsClientService {

	CurrencyConversionDTO getCurrencyConversion(CurrencyEnum sourceCurrency, CurrencyEnum destCurrency)
			throws KvsException, JsonException;

	void saveCurrencyConversion(CurrencyConversionDTO currencyConversion) throws KvsException;

	String buildKey(CurrencyEnum sourceCurrency, CurrencyEnum destCurrency);

}