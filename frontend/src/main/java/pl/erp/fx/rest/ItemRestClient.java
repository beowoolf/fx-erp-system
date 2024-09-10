package pl.erp.fx.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.erp.fx.dto.ItemDto;
import pl.erp.fx.dto.ItemEditViewDto;
import pl.erp.fx.dto.ItemSaveDto;
import pl.erp.fx.handler.ProcessFinishedHandler;

import java.util.Arrays;
import java.util.List;

public class ItemRestClient {

    private static final String ITEMS_URL = "http://localhost:8080/items";
    private static final String ITEM_EDIT_DATA_URL = "http://localhost:8080/item_edit_data";

    private final RestTemplate restTemplate;

    public ItemRestClient() {
        restTemplate = new RestTemplate();
    }

    public List<ItemDto> getItems() {
        ResponseEntity<ItemDto[]> itemResponseEntity = restTemplate.getForEntity(ITEMS_URL, ItemDto[].class);
        return Arrays.asList(itemResponseEntity.getBody());
    }

    public void saveItem(ItemSaveDto dto, ProcessFinishedHandler handler) {
        ResponseEntity<ItemDto> responseEntity = restTemplate.postForEntity(ITEMS_URL, dto, ItemDto.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()))
            handler.handle();
        else
            throw new RuntimeException("Can't save dto: " + dto);
    }

    public ItemDto getItem(Long idItem) {
        ResponseEntity<ItemDto> responseEntity = restTemplate.getForEntity(ITEMS_URL + "/" + idItem, ItemDto.class);
        return responseEntity.getBody();
    }

    public ItemEditViewDto getEditItemData(Long idItem) {
        ResponseEntity<ItemEditViewDto> responseEntity = restTemplate.getForEntity(ITEM_EDIT_DATA_URL + "/" + idItem, ItemEditViewDto.class);
        return responseEntity.getBody();
    }

    public void deleteItem(Long idItemToDelete) {
        restTemplate.delete(ITEMS_URL + "/" + idItemToDelete);
    }

}
