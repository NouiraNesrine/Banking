package com.drops.banking.web;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.entities.Beneficiary;
import com.drops.banking.entities.Customer;
import com.drops.banking.repositories.BeneficiaryRepository;
import com.drops.banking.services.implementation.AccountService;
import com.drops.banking.services.implementation.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/banker/customer/{id}")
    public Customer getCustomer(@PathVariable(name="id")Long id) throws NotFoundException {
        return customerService.getCustomerById(id);
    }
    @GetMapping("/banker/customers")
    public Collection<Customer> getCustomers() throws Exception {
        return customerService.getCustomers();
    }

    @GetMapping("/customer/{id}/accounts")
    public List<BankAccount> getBankAccounts(@PathVariable(name="id") Long id) throws NotFoundException{
        return customerService.getBankAccounts(customerService.getCustomerById(id));
    }
    @GetMapping("/banker/customer/{id}/accounts")
    public List<BankAccount> getAccounts(@PathVariable(name="id") Long id) throws NotFoundException{
        return customerService.getBankAccounts(customerService.getCustomerById(id));
    }
    @GetMapping("/customer{id}/beneficiaries")
    public List<Beneficiary> getBeneficiaries(@PathVariable(name="id")Long id) throws NotFoundException{
        return customerService.getCustomerById(id).getBeneficiaryList();
    }
    @PostMapping("/banker/customer")
    public void  addCustomer(@RequestBody Customer customer) throws Exception {
        customerService.createCustomer(customer);
    }
    @PutMapping("/banker/customer/{id}")
    public void updateCustomer(@PathVariable Long id ,@RequestBody Customer customer) throws Exception {
        customerService.updateCustomer(customer);
    }
    @DeleteMapping("/banker/customer/{id}")
    public void deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.removeCustomer(id);
    }
    @PostMapping("/customer/meeting")
    @ResponseStatus(HttpStatus.CREATED)
    public void setMeeting(@RequestBody Customer customer,@RequestBody Availability availability,@RequestParam(name = "issue")String issue ) throws Exception {
        customerService.requestMeeting(customer, availability,issue);
    }
    @GetMapping("/customer/username/{username}")
    public Customer getCustomerByUsername(@PathVariable String username) throws Exception {
        return customerService.getCustomerByUsername(username);
    }
    @GetMapping("/customer/beneficiary/{num}")
    public Beneficiary getBeneficiaryByAccountNumber(@PathVariable int num) throws NotFoundException {
        BankAccount account= accountService.getBankAccountByAccountNumber(num);
        Customer customer=account.getCustomer();
        Beneficiary beneficiary=new Beneficiary();
        beneficiary.setFirstName(customer.getFirstName());
        beneficiary.setLastName(customer.getLastName());
        beneficiary.setAccountNumber(num);
        beneficiaryRepository.save(beneficiary);
        return beneficiary;
    }

}
