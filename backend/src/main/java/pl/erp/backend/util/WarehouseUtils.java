package pl.erp.backend.util;

import pl.erp.backend.dto.WarehouseDto;
import pl.erp.backend.entity.Warehouse;

public class WarehouseUtils {

    public static WarehouseDto of(Warehouse warehouse) {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdWarehouse(warehouse.getIdWarehouse());
        dto.setName(warehouse.getName());
        return dto;
    }

}
