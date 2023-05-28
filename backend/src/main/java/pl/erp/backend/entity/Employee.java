package pl.erp.backend.entity;

import lombok.Data;
import pl.erp.backend.dto.EmployeeDto;

import javax.persistence.*;

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

    public static Employee of(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    public void updateEmployee(EmployeeDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }

}
