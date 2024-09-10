package pl.erp.backend.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperator;

    @Column
    private String login;

    @Column
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmployee")
    private Employee employee;

}
