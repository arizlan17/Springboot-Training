package org.example.springbootassignment.controller;

import org.example.springbootassignment.dto.BankAccountDto;
import org.example.springbootassignment.model.BankAccount;
import org.example.springbootassignment.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankAccount")
@CrossOrigin(origins = "*")

public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDto> getBankAccountByAccountNumber(@PathVariable long accountNumber){
    BankAccountDto bankAccountDto = bankAccountService.findAccountByAccountNumber(accountNumber);
    return ResponseEntity.ok(bankAccountDto);
    }

    @GetMapping("/nic/{nic}")
    public List<BankAccountDto> getAllBankAccounts(@PathVariable String nic){
        List<BankAccountDto> bankAccountDto = bankAccountService.findAllBankAccount(nic);
        return bankAccountDto;
    }
}
