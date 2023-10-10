package pl.erp.fx.dto;

import lombok.Data;

@Data
public class WarehouseDto {

    private Long idWarehouse;
    private String name;

    @Override
    public String toString() {
        return name;
    }

}
