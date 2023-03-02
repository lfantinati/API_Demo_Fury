package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import org.springframework.beans.factory.annotation.Value;

import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyConversionDTO;
import com.mercadolibre.bootcamp_demo_java_app.dtos.CurrencyEnum;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.exceptions.KvsException;

public abstract class AbstractCurrencyConversionKvsClientService extends KvsClientService<CurrencyConversionDTO> implements ICurrencyConversionKvsClientService {
	private static final int DEFAULT_CURRENCY_CONVERSION_TTL = 3600;
	
	public AbstractCurrencyConversionKvsClientService(@Value("${KEY_VALUE_STORE_CURRENCY_CONVERSIONS_CONTAINER_NAME}") String containerName) {
		super();
		this.containerName = containerName;
	}

	@Override
	public CurrencyConversionDTO getCurrencyConversion(CurrencyEnum sourceCurrency, CurrencyEnum destCurrency) throws KvsException, JsonException {
		return getValue(buildKey(sourceCurrency, destCurrency));
	}

	@Override
	public void saveCurrencyConversion(CurrencyConversionDTO currencyConversion) throws KvsException {
		String key = buildKey(currencyConversion.getBaseCurrency(), currencyConversion.getDestCurrency());
		saveValue(key, currencyConversion, DEFAULT_CURRENCY_CONVERSION_TTL);
	}
	
	
	@Override
	public String buildKey(CurrencyEnum sourceCurrency, CurrencyEnum destCurrency) {
		return String.format("%s_%s", sourceCurrency, destCurrency);
	}

	@Override
	protected Class<CurrencyConversionDTO> getTypedClass() {
		return CurrencyConversionDTO.class;
	}
}
