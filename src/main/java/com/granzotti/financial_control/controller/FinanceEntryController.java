package com.granzotti.financial_control.controller;

import com.granzotti.financial_control.model.FinanceEntry;
import com.granzotti.financial_control.service.FinanceEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finances")
public class FinanceEntryController {

    @Autowired
    private FinanceEntryService financeEntryService;

    @PostMapping
    public ResponseEntity<FinanceEntry> createFinanceEntry(@RequestBody FinanceEntry financeEntry) {
        FinanceEntry createdEntry = financeEntryService.createFinanceEntry(financeEntry);
        return ResponseEntity.ok(createdEntry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceEntry> getFinanceEntry(@PathVariable Long id) {
        FinanceEntry entry = financeEntryService.getFinanceEntry(id);
        return ResponseEntity.ok(entry);
    }

    @GetMapping
    public List<FinanceEntry> getAllEntries() {
        return financeEntryService.getAllEntries();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceEntry> updateFinanceEntry(@PathVariable Long id, @RequestBody FinanceEntry financeEntry) {
        FinanceEntry updatedEntry = financeEntryService.updateFinanceEntry(id, financeEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinanceEntry(@PathVariable Long id) {
        financeEntryService.deleteFinanceEntry(id);
        return ResponseEntity.noContent().build();
    }
}
