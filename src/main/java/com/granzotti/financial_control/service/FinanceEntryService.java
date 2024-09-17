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
        FinanceEntry entry = financeEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finance Entry not found"));
        validateOwnership(entry);
        return entry;
    }

    public List<FinanceEntry> getAllEntries() {
        User authenticatedUser = securityUtils.getAuthenticatedUser();
        return financeEntryRepository.findByUser(authenticatedUser);
    }

    public FinanceEntry updateFinanceEntry(Long id, FinanceEntry financeEntry) {
        FinanceEntry existingEntry = getFinanceEntry(id);
        validateOwnership(existingEntry);

        existingEntry.setName(financeEntry.getName());
        existingEntry.setValue(financeEntry.getValue());
        existingEntry.setDescription(financeEntry.getDescription());
        existingEntry.setDate(financeEntry.getDate());
        existingEntry.setType(financeEntry.getType());
        existingEntry.setCategory(financeEntry.getCategory());
        existingEntry.setFixed(financeEntry.isFixed());
        existingEntry.setPaid(financeEntry.isPaid());
        existingEntry.setInstallmentMonths(financeEntry.getInstallmentMonths());

        return financeEntryRepository.save(existingEntry);
    }

    public void deleteFinanceEntry(Long id) {
        FinanceEntry entry = getFinanceEntry(id);
        validateOwnership(entry);
        financeEntryRepository.deleteById(id);
    }
    
    private void validateOwnership(FinanceEntry entry) {
        User authenticatedUser = securityUtils.getAuthenticatedUser();
        if (!entry.getUser().equals(authenticatedUser)) {
            throw new RuntimeException("You cannot modify an entry that does not belong to you");
        }
    }
}
