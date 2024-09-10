package pl.erp.fx.util;

import pl.erp.fx.dto.EmployeeDto;
import pl.erp.fx.table.EmployeeTableModel;

public class EmployeeUtils {

    public static EmployeeTableModel of(EmployeeDto dto) {
        return new EmployeeTableModel(dto.getIdEmployee(), dto.getFirstName(), dto.getLastName(), dto.getSalary());
    }

}
