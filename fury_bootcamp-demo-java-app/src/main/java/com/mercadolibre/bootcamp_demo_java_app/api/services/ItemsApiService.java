package com.mercadolibre.bootcamp_demo_java_app.api.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.mercadolibre.json.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mercadolibre.bootcamp_demo_java_app.dtos.ItemDTO;
import com.mercadolibre.bootcamp_demo_java_app.dtos.ItemMultiGetResponseElementDTO;
import com.mercadolibre.bootcamp_demo_java_app.exceptions.InternalServerErrorException;
import com.mercadolibre.bootcamp_demo_java_app.exceptions.NotFoundException;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.restclient.exception.RestException;

@Service
public class ItemsApiService extends RestClientService{
    @Value("${meli.base.url}")
    private String baseUrl;

    private String itemsApiBaseUrl;

    private String itemsApiMultigetBaseUrl;
    
    private RestClient itemsApiClient;

    private static final String ITEMS_API_ATTRIBUTES_TO_FETCH_SUFFIX = "attributes=id,title,price,site_id,currency_id,accepts_mercadopago";

    public ItemsApiService() throws IOException{
    	super();
    }
    
    @PostConstruct
    public void init() throws IOException {
        itemsApiBaseUrl = baseUrl + "/items/%s?"+ITEMS_API_ATTRIBUTES_TO_FETCH_SUFFIX;
        itemsApiMultigetBaseUrl = baseUrl + "/items/?ids=%s&"+ITEMS_API_ATTRIBUTES_TO_FETCH_SUFFIX;
        itemsApiClient = RestClient.builder()
        	    .withPool(restPool)
        	    .build();
    }

    public ItemDTO getItemInfo(String itemId) throws ParseException, RestException{
        String currentItemUrl = String.format(itemsApiBaseUrl,itemId);
        Response resp = itemsApiClient.get(currentItemUrl);
        return handleApiResponse(resp, ItemDTO.class);
    }

    public Map<String, Optional<ItemDTO>> getItemsInfo(List<String> itemIds) throws ParseException, RestException{
        String currentItemsUrl = String.format(itemsApiMultigetBaseUrl, itemIds);
        Response resp = itemsApiClient.get(currentItemsUrl);
        ItemMultiGetResponseElementDTO[] responses = handleApiResponse(resp, ItemMultiGetResponseElementDTO[].class);
        return Arrays.stream(responses)
                .map(r -> {
                    String id = (String) r.getBody().get("id");
                    Optional<ItemDTO> value = null;
                    if (r.getCode() == 200){
                        value = Optional.of(new ItemDTO(r.getBody()));
                    } else {
                        value = Optional.empty();
                    }
                    return Map.entry(id, value);
                }).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

}
