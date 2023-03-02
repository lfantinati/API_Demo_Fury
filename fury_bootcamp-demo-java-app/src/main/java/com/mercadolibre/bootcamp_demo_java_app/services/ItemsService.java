package com.mercadolibre.bootcamp_demo_java_app.services;

import com.mercadolibre.bootcamp_demo_java_app.dtos.ItemMultiGetResponseElementDTO;
import com.mercadolibre.kvsclient.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.bootcamp_demo_java_app.api.services.ItemsApiService;
import com.mercadolibre.bootcamp_demo_java_app.dtos.ItemDTO;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.restclient.exception.RestException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemsService {
	private static final Logger log = LoggerFactory.getLogger(ItemsService.class);
	private ItemsApiService itemsApiService;
	
	private CurrencyConversionService currencyConversionService;
	
	@Autowired
	public ItemsService(ItemsApiService itemsApiService, CurrencyConversionService currencyConversionService) {
		this.itemsApiService = itemsApiService;
		this.currencyConversionService = currencyConversionService;
	}
	
	public ItemDTO getItemInfo(String itemId) throws ParseException, RestException {
		return itemsApiService.getItemInfo(itemId);
	}

	public Map<String,Optional<ItemDTO>> getItemsInfo(List<String> itemIds) throws RestException, ParseException {
		return itemsApiService.getItemsInfo(itemIds);
	}
	
	public Double getItemPrice(String itemId) throws ParseException, RestException {
		ItemDTO itemInfo = getItemInfo(itemId);
		if (log.isDebugEnabled()) {
			log.debug("Item info lookup: {}",itemInfo);
		}
		return itemInfo.getPrice();
	}

	public Double getItemUSDPrice(String itemId) throws ParseException, RestException, KvsException, JsonException {
		ItemDTO itemInfo = getItemInfo(itemId);
		Double conversionRate = currencyConversionService.getCurrencyConversionRatioToUSD(itemInfo.getCurrencyId());
		return conversionRate * itemInfo.getPrice();
	}

	public void processFeedUpdate(String itemId) throws ParseException, RestException, KvsException, JsonException {
		ItemDTO itemInfo = getItemInfo(itemId);
		if (itemInfo.isAcceptsMercadoPago()) {
			Double itemUSDPrice = getItemUSDPrice(itemId);
			log.info("Item {} price is {}",itemId, itemUSDPrice);
		}
	}

	public Map<String,Integer> processFeedUpdates(List<String> itemIds) throws RestException, ParseException {
		Map<String, Optional<ItemDTO>> itemsInfo = getItemsInfo(itemIds);
		return itemIds.stream().map(itemId -> {
			Optional<ItemDTO> itemInfoOpt = itemsInfo.get(itemId);
			if (itemInfoOpt.isPresent()){
				try {
					if (itemInfoOpt.get().isAcceptsMercadoPago()) {
						Double itemUSDPrice = getItemUSDPrice(itemId);
						log.info("Item {} price is {}",itemId, itemUSDPrice);
					}
					return Map.entry(itemId, 200);
				} catch (Exception e){
					return Map.entry(itemId, 500);
				}
			} else {
				return Map.entry(itemId, 200);
			}
		}).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}
}
