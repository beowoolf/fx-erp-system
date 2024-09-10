package pl.erp.backend.dto;

import lombok.Data;

@Data
public class ItemDto {

    private Long idItem;
    private String name;
    private Double quantity;
    private String quantityType;

}
