package pl.erp.fx.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.erp.fx.dto.QuantityTypeDto;

import java.util.Arrays;
import java.util.List;

public class QuantityTypeRestClient {

    private static final String QUANTITY_TYPES_URL = "http://localhost:8080/quantity_types";

    private final RestTemplate restTemplate;

    public QuantityTypeRestClient() {
        restTemplate = new RestTemplate();
    }

    public List<QuantityTypeDto> getQuantityTypes() {
        ResponseEntity<QuantityTypeDto[]> quantityTypeResponseEntity = restTemplate.getForEntity(QUANTITY_TYPES_URL, QuantityTypeDto[].class);
        return Arrays.asList(quantityTypeResponseEntity.getBody());
    }

}
