package com.granzotti.financial_control.service;

import com.granzotti.financial_control.model.FinanceEntry;
import com.granzotti.financial_control.model.User;
import com.granzotti.financial_control.repository.FinanceEntryRepository;
import com.granzotti.financial_control.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceEntryService {

    @Autowired
    private FinanceEntryRepository financeEntryRepository;

    @Autowired
    private SecurityUtils securityUtils;

    public FinanceEntry createFinanceEntry(FinanceEntry financeEntry) {
        User authenticatedUser = securityUtils.getAuthenticatedUser();
        financeEntry.setUser(authenticatedUser);
        return financeEntryRepository.save(financeEntry);
    }

    public FinanceEntry getFinanceEntry(Long id) {
        return financeEntryRepository.findById(id).orElseThrow();
    }

    public List<FinanceEntry> getAllEntries() {
        return financeEntryRepository.findAll();
    }
}
