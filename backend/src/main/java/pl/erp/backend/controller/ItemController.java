package pl.erp.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.erp.backend.dto.ItemDto;
import pl.erp.backend.dto.ItemEditViewDto;
import pl.erp.backend.dto.ItemSaveDto;
import pl.erp.backend.entity.Item;
import pl.erp.backend.repository.ItemRepository;
import pl.erp.backend.repository.QuantityTypeRepository;
import pl.erp.backend.service.ItemService;
import pl.erp.backend.util.ItemUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final QuantityTypeRepository quantityTypeRepository;

    @PostMapping("/items")
    public ItemDto newItem(@RequestBody ItemSaveDto dto) {
        if (dto.getIdItem() == null)
            return ItemUtils.of(itemService.saveItem(dto));
        else {
            Item item = itemRepository.findById(dto.getIdItem()).get();
            item.setName(dto.getName());
            item.setQuantity(dto.getQuantity());
            item.setQuantityType(quantityTypeRepository.findById(dto.getIdQuantityType()).get());
            return ItemUtils.of(itemRepository.save(item));
        }
    }

    @GetMapping("/items")
    public List<ItemDto> listItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemUtils::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/items/{idItem}")
    public ItemDto getItem(@PathVariable Long idItem) throws InterruptedException {
        Optional<Item> optionalItem = itemRepository.findById(idItem);
        return ItemUtils.of(optionalItem.get());
    }

    @GetMapping("/item_edit_data/{idItem}")
    public ItemEditViewDto getItemEditDto(@PathVariable Long idItem) {
        Item item = itemRepository.findById(idItem).get();
        ItemEditViewDto dto = ItemUtils.of(item, quantityTypeRepository.findAll());
        return dto;
    }

    @DeleteMapping("/items/{idItem}")
    public ResponseEntity deleteItem(@PathVariable Long idItem) {
        itemRepository.deleteById(idItem);
        return ResponseEntity.ok().build();
    }

}
