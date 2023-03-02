package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mercadolibre.kvsclient.ContainerKvsLowLevelClient;
import com.mercadolibre.kvsclient.IContainerKvsClient;
import com.mercadolibre.kvsclient.kvsapi.KvsapiConfiguration;

@Service
@Profile({"prod","test"})
public class CurrencyConversionKvsClientService extends AbstractCurrencyConversionKvsClientService {	
	public CurrencyConversionKvsClientService(@Value("${KEY_VALUE_STORE_CURRENCY_CONVERSIONS_CONTAINER_NAME}") String containerName) {
		super(containerName);
	}
	
	@Override
	protected IContainerKvsClient initKVS() {
		KvsapiConfiguration config = KvsapiConfiguration.builder() 
                .withSocketTimeout(150) //all of this are default values 
                .withMaxWait(100) 
                .withConnectionTimeout(100)
                .withMaxConnections(30) 
                .withMaxConnectionsPerRoute(30) 
                .withMaxRetries(1) 
                .withRetryDelay(30).build();
		return new ContainerKvsLowLevelClient(config, containerName);
	}
}
