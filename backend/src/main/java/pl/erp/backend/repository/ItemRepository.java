package pl.erp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.erp.backend.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
