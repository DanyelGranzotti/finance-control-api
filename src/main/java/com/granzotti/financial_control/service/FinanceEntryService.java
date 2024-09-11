package com.granzotti.financial_control.service;

import com.granzotti.financial_control.model.FinanceEntry;
import com.granzotti.financial_control.repository.FinanceEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceEntryService {

    @Autowired
    private FinanceEntryRepository financeEntryRepository;

    public FinanceEntry createFinanceEntry(FinanceEntry entry) {
        return financeEntryRepository.save(entry);
    }

    public FinanceEntry getFinanceEntry(Long id) {
        return financeEntryRepository.findById(id).orElseThrow();
    }

    public List<FinanceEntry> getAllEntries() {
        return financeEntryRepository.findAll();
    }
}
