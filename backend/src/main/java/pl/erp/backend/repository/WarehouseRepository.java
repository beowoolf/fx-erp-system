package pl.erp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.erp.backend.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
