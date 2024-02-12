package pl.erp.backend.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWarehouse;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse")
    private List<Item> items;

}
