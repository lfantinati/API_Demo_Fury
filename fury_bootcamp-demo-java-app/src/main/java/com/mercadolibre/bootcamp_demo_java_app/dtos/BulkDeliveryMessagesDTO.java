package com.mercadolibre.bootcamp_demo_java_app.dtos;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BulkDeliveryMessagesDTO<T> {
	@JsonProperty("messages")
	Collection<ConsumerMessageDTO<T>> messages;
}
