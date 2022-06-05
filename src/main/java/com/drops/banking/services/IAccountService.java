package com.drops.banking.services;

import com.drops.banking.dtos.AccountHistoryDTO;
import com.drops.banking.dtos.AccountOperationDTO;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.entities.Customer;
import com.drops.banking.exceptions.BalanceNotSufficientException;
import javassist.NotFoundException;

import java.util.List;

public interface IAccountService {

    List<BankAccount> getBankAccounts()throws Exception;
    BankAccount getBankAccountByAccountNumber(int accountNumber)throws NotFoundException;
    BankAccount getBankAccountById(Long id)throws NotFoundException;
    void createBankAccount(BankAccount bankAccount);
    void updateBankAccount(BankAccount bankAccount)throws NotFoundException;
    void removeBanAccount(Long id);
    void createCurrentBankAccount(double initialBalance, double overDraft, Customer customer);
    void createSavingBankAccount(double initialBalance, double interestRate, Customer customer);
    void debit(BankAccount bankAccount, double amount, String description)throws BalanceNotSufficientException;
    void credit(BankAccount bankAccount, double amount, String description);
    List<AccountOperationDTO> bankAccountHistory(BankAccount bankAccount);
    AccountHistoryDTO getAccountHistory(BankAccount bankAccount, int page, int size);
    void transfer(BankAccount bankAccount, Long beneficiaryId, double amount) throws BalanceNotSufficientException;


}
