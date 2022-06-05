package com.drops.banking.services;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.entities.Customer;
import javassist.NotFoundException;

import java.util.List;

public interface ICustomerService {
    List<Customer> getCustomers() throws Exception;
    Customer getCustomerById(Long id) throws NotFoundException;
    Customer getCustomerByUsername(String username) throws NotFoundException;
    void createCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws NotFoundException;
    void removeCustomer(Long id) throws NotFoundException;
    List<Availability> getAvailabilities(Customer customer);
    List<BankAccount> getBankAccounts(Customer customer);
    void requestMeeting(Customer customer,Availability availability,String issue)throws Exception;
}
