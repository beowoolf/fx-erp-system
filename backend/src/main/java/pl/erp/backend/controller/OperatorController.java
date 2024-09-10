package pl.erp.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.erp.backend.dto.OperatorAuthenticationResultDto;
import pl.erp.backend.dto.OperatorCredentialsDto;
import pl.erp.backend.entity.Operator;
import pl.erp.backend.repository.OperatorRepository;
import pl.erp.backend.util.OperatorAuthenticationResultUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;

    @PostMapping("/operators")
    public Operator newOperator(@RequestBody Operator newOperator) {
        return operatorRepository.save(newOperator);
    }

    @GetMapping("/operators")
    public List<Operator> listOperators() {
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operators")
    public ResponseEntity deleteOperator(@RequestBody Long idOperator) {
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify_operator_credentials")
    public OperatorAuthenticationResultDto verifyOperatorCredentials(@RequestBody OperatorCredentialsDto operatorCredentialsDto) {
        Optional<Operator> operatorOptional = operatorRepository.findByLogin(operatorCredentialsDto.getLogin());
        if (!operatorOptional.isPresent())
            return OperatorAuthenticationResultUtils.createUnauthenticated();
        Operator operator = operatorOptional.get();
        if (!operator.getPassword().equals(operatorCredentialsDto.getPassword()))
            return OperatorAuthenticationResultUtils.createUnauthenticated();
        else
            return OperatorAuthenticationResultUtils.of(operator);
    }

}
