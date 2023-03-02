package com.mercadolibre.bootcamp_demo_java_app.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mercadolibre.bootcamp_demo_java_app.dtos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.bootcamp_demo_java_app.services.ItemsService;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.restclient.exception.RestException;

@RestController
@Slf4j
public class ConsumerController {
	ItemsService itemsService;

	@Autowired
	public ConsumerController(ItemsService itemsService) {
		this.itemsService = itemsService;
	}
	
	@PostMapping("/consume-items-price-change/single")
	public ResponseEntity<Boolean> processSingleUpdate(@RequestBody @Valid ConsumerMessageDTO<Map<String,Object>> dto) throws ParseException, RestException, KvsException, JsonException {
		Map<String,Object> feedUpdateData = dto.getMessage();
		String itemId = (String) feedUpdateData.get("id"); 
		itemsService.processFeedUpdate(itemId);
		return ResponseEntity.ok(true);
	}

	@PostMapping("/consume-items-price-change/bulk")
	public ResponseEntity<BulkDeliveryResponseDTO> processBulkUpdate(@RequestBody @Valid BulkDeliveryMessagesDTO<ItemMessagePayloadDTO> allMessages) throws ParseException, RestException, KvsException, JsonException {
		try {
			Collection<ConsumerMessageDTO<ItemMessagePayloadDTO>> messages = allMessages.getMessages();
			List<String> itemIds = messages.stream().map(i -> i.getId()).collect(Collectors.toList());
			Map<String,Integer> results = itemsService.processFeedUpdates(itemIds);
			Collection<BulkDeliveryResponseElementDTO> responses = results.entrySet().stream()
					.map(e -> new BulkDeliveryResponseElementDTO(e.getKey(),e.getValue())).collect(Collectors.toList());
			BulkDeliveryResponseDTO resp = new BulkDeliveryResponseDTO(responses);
			return ResponseEntity.ok(resp);
		} catch (Exception e){
			//this will return a general 500 status code mapped by Spring
			log.error("Error processing bulk update for payload:%s"+allMessages,e);
			throw e;
		}
	}
}
