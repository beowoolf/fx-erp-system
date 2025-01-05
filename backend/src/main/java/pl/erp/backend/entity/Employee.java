package pl.erp.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.erp.backend.dto.EmployeeDto;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String salary;

    @OneToOne(mappedBy = "employee")
    private Operator operator;

    public void updateEmployee(EmployeeDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }

}
