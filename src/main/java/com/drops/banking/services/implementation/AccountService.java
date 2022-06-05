package com.drops.banking.services.implementation;

import com.drops.banking.dtos.AccountHistoryDTO;
import com.drops.banking.dtos.AccountOperationDTO;
import com.drops.banking.entities.*;
import com.drops.banking.enums.OperationType;
import com.drops.banking.exceptions.BalanceNotSufficientException;
import com.drops.banking.mappers.BankAccountMapper;
import com.drops.banking.repositories.AccountOperationRepository;
import com.drops.banking.repositories.BankAccountRepository;
import com.drops.banking.services.IAccountService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapper bankAccountMapper;

    @Override
    public  List<BankAccount> getBankAccounts() throws Exception{
        List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
        bankAccountList = bankAccountRepository.findAll();
        if(bankAccountList.size()<1){
            throw new Exception("No BankAccount found");
        }
        return bankAccountList;
    }
    @Override
    public BankAccount getBankAccountByAccountNumber(int accountNumber) throws NotFoundException {
        return bankAccountRepository.findBankAccountByAccountNumber(accountNumber).orElseThrow(()->
                new NotFoundException("No bank account with this account number: "+accountNumber));
    }
    @Override
    public BankAccount getBankAccountById(Long id) throws NotFoundException{
        return bankAccountRepository.findById(id).orElseThrow(()->
                new NotFoundException("No bank account with this id: "+id));
    }
    @Override
    public void createBankAccount(BankAccount bankAccount){
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void updateBankAccount(BankAccount bankAccount) throws NotFoundException{
        if(bankAccountRepository.findById(bankAccount.getId()).isEmpty()) throw new NotFoundException("No bank account with this id: "+bankAccount.getId());
        BankAccount bankAccountTobeUpdated = getBankAccountById(bankAccount.getId());
        bankAccountTobeUpdated.setStatus(bankAccount.getStatus());
        bankAccountTobeUpdated.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountTobeUpdated.setBalance(bankAccount.getBalance());
        bankAccountTobeUpdated.setAgency(bankAccount.getAgency());
        bankAccountTobeUpdated.setCustomer(bankAccount.getCustomer());
        bankAccountTobeUpdated.setCreatedAt(bankAccount.getCreatedAt());
        bankAccountRepository.save(bankAccountTobeUpdated);
    }
    @Override
    public void removeBanAccount(Long id){
        bankAccountRepository.deleteById(id);
    }
    @Override
    public void createCurrentBankAccount(double initialBalance, double overDraft, Customer customer)  {

        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        bankAccountRepository.save(currentAccount);
    }
    @Override
    public void createSavingBankAccount(double initialBalance, double interestRate, Customer customer) {
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        bankAccountRepository.save(savingAccount);
    }
    @Override
    public void debit(BankAccount bankAccount, double amount, String description) throws BalanceNotSufficientException {
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void credit(BankAccount bankAccount, double amount, String description) {
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public List<AccountOperationDTO> bankAccountHistory(BankAccount bankAccount){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(bankAccount.getId());
        return accountOperations.stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }
    @Override
    public AccountHistoryDTO getAccountHistory(BankAccount bankAccount, int page, int size){
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(bankAccount.getId(), PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
    @Override
    public void transfer(BankAccount bankAccount, Long beneficiaryId, double amount) throws BalanceNotSufficientException {

        debit(bankAccount,amount,"Transfer to "+ beneficiaryId);
        credit(bankAccount,amount,"Transfer from "+ beneficiaryId);
    }

}
