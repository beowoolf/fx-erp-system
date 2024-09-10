package pl.erp.backend.util;

import pl.erp.backend.dto.QuantityTypeDto;
import pl.erp.backend.entity.QuantityType;

public class QuantityTypeUtils {

    public static QuantityTypeDto of(QuantityType quantityType) {
        QuantityTypeDto dto = new QuantityTypeDto();
        dto.setName(quantityType.getName());
        dto.setIdQuantityType(quantityType.getIdQuantityType());
        return dto;
    }

}
