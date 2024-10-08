package com.granzotti.financial_control.repository;


import com.granzotti.financial_control.model.FinanceEntry;
import com.granzotti.financial_control.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceEntryRepository extends JpaRepository<FinanceEntry, Long> {
    List<FinanceEntry> findByUserId(Long userId);

    List<FinanceEntry> findByUser(User user);
}
