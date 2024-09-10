package pl.erp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.erp.backend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
