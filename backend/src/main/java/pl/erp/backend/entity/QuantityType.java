package pl.erp.backend.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class QuantityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuantityType;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quantityType")
    private List<Item> items;

}
