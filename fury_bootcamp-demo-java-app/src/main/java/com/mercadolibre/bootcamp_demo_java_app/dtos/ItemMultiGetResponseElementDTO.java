package com.mercadolibre.bootcamp_demo_java_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ItemMultiGetResponseElementDTO {
    @JsonProperty
    private int code;

    @JsonProperty
    private Map<String,Object> body;
}
