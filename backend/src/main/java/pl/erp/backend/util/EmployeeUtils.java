package pl.erp.backend.util;

import pl.erp.backend.dto.EmployeeDto;
import pl.erp.backend.entity.Employee;

public class EmployeeUtils {

    public static Employee of(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    public static EmployeeDto of(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setIdEmployee(employee.getIdEmployee());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setSalary(employee.getSalary());
        return dto;
    }

}
