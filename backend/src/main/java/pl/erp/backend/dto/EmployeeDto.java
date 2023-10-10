package pl.erp.backend.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long idEmployee;
    private String firstName;
    private String lastName;
    private String salary;

}
