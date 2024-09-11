package com.granzotti.financial_control.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class FinanceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double value;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private FinanceType type;

    @ManyToOne
    private Category category;

    private boolean isFixed;
    private boolean isPaid;
    private Integer installmentMonths;

    @ManyToOne
    private User user;

}
