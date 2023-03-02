package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mercadolibre.kvsclient.IContainerKvsClient;

@Service
@Profile({"local","integration_test"})
public class LocalCurrencyConversionKvsClientService extends AbstractCurrencyConversionKvsClientService {
	private LocalContainerKvsClient kvsClient;
	
	public LocalCurrencyConversionKvsClientService(@Value("${KEY_VALUE_STORE_CURRENCY_CONVERSIONS_CONTAINER_NAME}") String containerName, LocalContainerKvsClient localContainerKvsClient) {
		super(containerName);
		this.kvsClient = localContainerKvsClient;
	}

	@Override
	protected IContainerKvsClient initKVS() {
		return kvsClient;
	}
}