package com.mercadolibre.bootcamp_demo_java_app.dtos;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ConsumerMessageDTO<T> {
	@JsonProperty("id")
	private String id;	

	@NotNull
	@JsonProperty("msg")
	private T message;
	
	@NotNull
	@JsonProperty("publish_time")
	private Long publishTime;
}
