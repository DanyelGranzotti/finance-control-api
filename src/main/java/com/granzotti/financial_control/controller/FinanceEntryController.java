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
    public ResponseEntity<FinanceEntry> createFinanceEntry(@RequestBody FinanceEntry entry) {
        return ResponseEntity.ok(financeEntryService.createFinanceEntry(entry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceEntry> getFinanceEntry(@PathVariable Long id) {
        return ResponseEntity.ok(financeEntryService.getFinanceEntry(id));
    }

    @GetMapping
    public List<FinanceEntry> getAllEntries() {
        return financeEntryService.getAllEntries();
    }
}
