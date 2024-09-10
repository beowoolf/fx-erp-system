package pl.erp.backend.util;

import pl.erp.backend.dto.ItemDto;
import pl.erp.backend.dto.ItemEditViewDto;
import pl.erp.backend.dto.ItemSaveDto;
import pl.erp.backend.entity.Item;
import pl.erp.backend.entity.QuantityType;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemEditViewDto of(Item item, List<QuantityType> quantityTypeList) {
        ItemEditViewDto dto = new ItemEditViewDto();
        dto.setIdItem(item.getIdItem());
        dto.setName(item.getName());
        dto.setQuantity(item.getQuantity());
        dto.setIdQuantityType(item.getQuantityType().getIdQuantityType());
        dto.setQuantityTypeDtoList(quantityTypeList.stream().map(QuantityTypeUtils::of).collect(Collectors.toList()));
        return dto;
    }

    public static ItemDto of(Item item) {
        ItemDto dto = new ItemDto();
        dto.setIdItem(item.getIdItem());
        dto.setName(item.getName());
        dto.setQuantity(item.getQuantity());
        dto.setQuantityType(item.getQuantityType().getName());
        return dto;
    }

    public static Item of(ItemSaveDto dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        return item;
    }

}
