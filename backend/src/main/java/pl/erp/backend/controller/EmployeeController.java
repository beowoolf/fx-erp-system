package pl.erp.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.erp.backend.dto.EmployeeDto;
import pl.erp.backend.entity.Employee;
import pl.erp.backend.repository.EmployeeRepository;
import pl.erp.backend.util.EmployeeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    EmployeeDto saveOrUpdateEmployee(@RequestBody EmployeeDto dto) {
        if (dto.getIdEmployee() == null)
            return EmployeeUtils.of(employeeRepository.save(EmployeeUtils.of(dto)));
        else {
            Optional<Employee> optionalEmployee = employeeRepository.findById(dto.getIdEmployee());
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                employee.updateEmployee(dto);
                return EmployeeUtils.of(employeeRepository.save(employee));
            } else
                throw new RuntimeException("Can't find user with given id: " + dto.getIdEmployee());
        }
    }

    @GetMapping("/employees")
    List<EmployeeDto> listEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeUtils::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/employees/{idEmployee}")
    public EmployeeDto getEmployee(@PathVariable Long idEmployee) throws InterruptedException {
        Thread.sleep(500); // to simulate loading
        Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
        return EmployeeUtils.of(optionalEmployee.get());
    }

    @DeleteMapping("/employees/{idEmployee}")
    ResponseEntity deleteEmployee(@PathVariable Long idEmployee) {
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }

}
