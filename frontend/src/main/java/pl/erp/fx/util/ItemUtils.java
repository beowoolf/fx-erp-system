package pl.erp.fx.util;

import pl.erp.fx.dto.ItemDto;
import pl.erp.fx.table.ItemTableModel;

public class ItemUtils {

    public static ItemTableModel of(ItemDto dto) {
        return new ItemTableModel(dto.getIdItem(), dto.getName(), dto.getQuantity(), dto.getQuantityType());
    }

}
