package com.mercadolibre.bootcamp_demo_java_app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.bootcamp_demo_java_app.api.services.CurrencyConversionApiService;
import com.mercadolibre.bootcamp_demo_java_app.api.services.kvs.ICurrencyConversionKvsClientService;
import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyConversionDTO;
import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyEnum;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.restclient.exception.RestException;

@Service
public class CurrencyConversionService {
	private static final Logger log = LoggerFactory.getLogger(CurrencyConversionService.class);
	private CurrencyConversionApiService currencyConversionApiService;
	
	private ICurrencyConversionKvsClientService currencyConversionKvsClientService;
	
	@Autowired
	public CurrencyConversionService(CurrencyConversionApiService currencyConversionApiService,
			ICurrencyConversionKvsClientService currencyConversionKvsClientService) {
		this.currencyConversionApiService = currencyConversionApiService;
		this.currencyConversionKvsClientService = currencyConversionKvsClientService;
	}
	
	public Double getCurrencyConversionRatioToUSD(CurrencyEnum sourceCurrency) throws ParseException, RestException, KvsException, JsonException {
		return getCurrencyConversionRatio(sourceCurrency, CurrencyEnum.USD);
	}
	
	public Double getCurrencyConversionRatio(CurrencyEnum sourceCurrency, CurrencyEnum destCurrency) throws KvsException, JsonException, RestException, ParseException {
		CurrencyConversionDTO currencyConversion = currencyConversionKvsClientService.getCurrencyConversion(sourceCurrency, destCurrency);
		if (currencyConversion == null) {
			currencyConversion = currencyConversionApiService.getCurrencyConversion(sourceCurrency,destCurrency);
			if (log.isDebugEnabled()) {
				log.debug("Currency conversion info lookup: {}",currencyConversion);
			}
			currencyConversionKvsClientService.saveCurrencyConversion(currencyConversion);
		}
		return currencyConversion.getRatio();
	}
}
