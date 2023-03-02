package com.mercadolibre.bootcamp_demo_java_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {	
	@JsonProperty("id")
    private String itemId;
	
	@JsonProperty("title")
    private String title;
	
	@JsonProperty("price")
    private Double price;
	
	@JsonProperty("site_id")
    private String siteId;
	
	@JsonProperty("currency_id")
    private CurrencyEnum currencyId;
	
	@JsonProperty("accepts_mercadopago")
	private boolean acceptsMercadoPago;

	@Builder
	public ItemDTO(Map<String, Object> body) {
		this.itemId = (String) body.get("id");
		this.title = (String) body.get("title");
		this.price = ((Number) body.get("price")).doubleValue();
		this.siteId = (String) body.get("site_id");
		this.currencyId = CurrencyEnum.valueOf((String) body.get("currency_id"));
		this.acceptsMercadoPago = (Boolean) body.get("accepts_mercadopago");
	}
}