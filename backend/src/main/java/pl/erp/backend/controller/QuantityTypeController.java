package pl.erp.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.erp.backend.dto.QuantityTypeDto;
import pl.erp.backend.entity.QuantityType;
import pl.erp.backend.repository.QuantityTypeRepository;
import pl.erp.backend.util.QuantityTypeUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class QuantityTypeController {

    private final QuantityTypeRepository quantityTypeRepository;

    @PostMapping("/quantity_types")
    QuantityType newQuantityType(@RequestBody QuantityType quantityType) {
        return quantityTypeRepository.save(quantityType);
    }

    @GetMapping("/quantity_types")
    List<QuantityTypeDto> listQuantityTypes() {
        return quantityTypeRepository.findAll().stream().map(QuantityTypeUtils::of).collect(Collectors.toList());
    }

    @DeleteMapping("/quantity_types")
    ResponseEntity deleteQuantityType(@RequestBody Long idQuantityType) {
        quantityTypeRepository.deleteById(idQuantityType);
        return ResponseEntity.ok().build();
    }

}
