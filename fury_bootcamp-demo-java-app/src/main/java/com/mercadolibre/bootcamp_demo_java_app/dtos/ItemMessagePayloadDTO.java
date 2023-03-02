package com.mercadolibre.bootcamp_demo_java_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemMessagePayloadDTO {
    @JsonProperty
    private String id;

    @JsonProperty
    private String categoryId;

    @JsonProperty
    private String sellerId;
}
