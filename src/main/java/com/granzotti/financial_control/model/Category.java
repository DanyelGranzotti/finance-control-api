package com.granzotti.financial_control.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

}
