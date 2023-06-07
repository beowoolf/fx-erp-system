package pl.erp.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.erp.backend.dto.ItemSaveDto;
import pl.erp.backend.entity.Item;
import pl.erp.backend.entity.QuantityType;
import pl.erp.backend.entity.Warehouse;
import pl.erp.backend.repository.ItemRepository;
import pl.erp.backend.repository.QuantityTypeRepository;
import pl.erp.backend.repository.WarehouseRepository;
import pl.erp.backend.util.ItemUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;
    private final QuantityTypeRepository quantityTypeRepository;

    public Item saveItem(ItemSaveDto dto) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(dto.getIdWarehouse());
        Optional<QuantityType> quantityTypeOptional = quantityTypeRepository.findById(dto.getIdQuantityType());
        if (!warehouseOptional.isPresent() || !quantityTypeOptional.isPresent())
            throw new RuntimeException("Incorrect identifiers: id:warehouse: "
                    + dto.getIdWarehouse() + ", idQuantityType:" + dto.getIdQuantityType());
        Warehouse warehouse = warehouseOptional.get();
        QuantityType quantityType = quantityTypeOptional.get();
        Item item = ItemUtils.of(dto);
        item.setQuantityType(quantityType);
        item.setWarehouse(warehouse);
        return itemRepository.save(item);
    }

}
