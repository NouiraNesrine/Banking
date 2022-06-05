package com.drops.banking.web;

import com.drops.banking.dtos.AccountHistoryDTO;
import com.drops.banking.dtos.AccountOperationDTO;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.exceptions.BalanceNotSufficientException;
import com.drops.banking.services.implementation.AccountService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BankAccountRest {

    @Autowired
    private AccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccount getBankAccount(@PathVariable Long accountId) throws NotFoundException {
        return bankAccountService.getBankAccountById(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccount> listAccounts() throws Exception {
        return bankAccountService.getBankAccounts();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable Long accountId) throws NotFoundException {
        return bankAccountService.bankAccountHistory(bankAccountService.getBankAccountById(accountId));
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable Long accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws NotFoundException {
        return bankAccountService.getAccountHistory(bankAccountService.getBankAccountById(accountId),page,size);
    }
    @PostMapping("/accounts/{accountId}/debit")
    public void debit(@PathVariable Long accountId,@RequestParam(name="amount") double amount,@RequestParam(name="description") String description) throws BalanceNotSufficientException, NotFoundException {
        this.bankAccountService.debit(bankAccountService.getBankAccountById(accountId),amount,description);

    }
    @PostMapping("/accounts/{accountId}/credit")
    public void credit(@PathVariable Long accountId,@RequestParam(name="amount") double amount,@RequestParam(name="description") String description) throws NotFoundException {
        this.bankAccountService.credit(bankAccountService.getBankAccountById(accountId),amount,description);

    }
    @PostMapping("/accounts/{accountId}/transfer")
    public void transfer(@PathVariable Long accountId,@RequestParam(name="amount") double amount,@RequestParam(name="beneficiaryId") Long beneficiaryId) throws BalanceNotSufficientException, NotFoundException {
        this.bankAccountService.transfer(bankAccountService.getBankAccountById(accountId),beneficiaryId,amount);
    }
}
