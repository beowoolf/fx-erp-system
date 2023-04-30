package pl.erp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.erp.backend.entity.Operator;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    Optional<Operator> findByLogin(String login);

}
