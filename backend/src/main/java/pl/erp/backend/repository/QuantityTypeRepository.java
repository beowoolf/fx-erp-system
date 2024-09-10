package pl.erp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.erp.backend.entity.QuantityType;

public interface QuantityTypeRepository extends JpaRepository<QuantityType, Long> {
}
