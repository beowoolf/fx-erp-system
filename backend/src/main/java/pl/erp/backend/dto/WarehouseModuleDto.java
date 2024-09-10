package pl.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModuleDto {

    private WarehouseDto selectedWarehouse;
    private List<WarehouseDto> warehouseDtoList;
    private List<ItemDto> itemDtoList;

}
