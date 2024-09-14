package com.granzotti.financial_control.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "finance_entries")
public class FinanceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceType type;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private boolean isFixed = false;
    @Column(nullable = false)
    private boolean isPaid = true;

    private Integer installmentMonths;

    @ManyToOne
    private User user;

}
