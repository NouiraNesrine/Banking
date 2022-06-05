package com.drops.banking.services.implementation;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.entities.Customer;
import com.drops.banking.entities.Meeting;
import com.drops.banking.repositories.CustomerRepository;
import com.drops.banking.repositories.MeetingRepository;
import com.drops.banking.services.ICustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankerService bankerService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Override
    public List<Customer> getCustomers() throws Exception{
        List<Customer> customerList = new ArrayList<Customer>();
        customerList = customerRepository.findAll();
        if(customerList.size()<1){
            throw new Exception("No customer found");
        }
        return customerList;
    }
    @Override
    public Customer getCustomerById(Long id) throws NotFoundException{
        return customerRepository.findById(id).orElseThrow(()->
                new NotFoundException("No customer with this id: "+id));
    }
    @Override
    public Customer getCustomerByUsername(String username) throws NotFoundException{
        return customerRepository.findCustomerByUsername(username).orElseThrow(()->
                new NotFoundException("No customer with this usrname: "+username));
    }
    @Override
    public void createCustomer(Customer customer) throws Exception{
        if(customerRepository.findCustomerByCin(customer.getCin()).isPresent())
            throw new Exception("this customer cin already exist");
        if(customer.getPassword().length()<1)
            throw new Exception("this password is invalid");

        customer.setCreationDate(new Date());
        customer.setBanker(bankerService.getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        customer.setAgency(bankerService.getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getAgency());
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        customerRepository.save(customer);
        emailService.sendEmail(customer);
    }
    @Override
    public void updateCustomer(Customer customer) throws NotFoundException{
        if(customerRepository.findById(customer.getId()).isPresent())
            throw new NotFoundException("this customer cin already exist");
        Customer customerToBeUpdated= getCustomerById(customer.getId());
        customerToBeUpdated.setCin(customer.getCin());
        customerToBeUpdated.setEmail(customer.getEmail());
        customerToBeUpdated.setBirthday(customer.getBirthday());
        customerToBeUpdated.setPhone(customer.getPhone());
        customerToBeUpdated.setAddress(customer.getAddress());
        customerToBeUpdated.setUsername(customer.getUsername());
        customerToBeUpdated.setSex(customer.getSex());
        customerToBeUpdated.setProfileStatus(customer.getProfileStatus());
        customerRepository.save(customer);

    }
    @Override
    public void removeCustomer(Long id) throws NotFoundException{
        if(customerRepository.findById(id).isPresent())
            throw new NotFoundException("this customer cin already exist");
        customerRepository.delete(getCustomerById(id));
    }
    @Override
    public List<Availability> getAvailabilities(Customer customer){
        return customer.getBanker().getAvailabilityList();
    }
    @Override
    public List<BankAccount> getBankAccounts(Customer customer){
        return customer.getBankAccounts();
    }
    @Override
    public void requestMeeting(Customer customer,Availability availability,String issue)throws Exception{
        Meeting meeting = new Meeting();
        meeting.setCustomer(customer);
        meeting.setBanker(customer.getBanker());
        meeting.setAvailability(availability);
        meeting.setAbout(issue);
        List<Availability> availabilityList = getAvailabilities(customer);
        availabilityList.removeIf(avai -> avai.getStartAt() == availability.getStartAt() && avai.getFinishAt() == availability.getFinishAt());
        meetingRepository.save(meeting);
        customerRepository.save(customer);

    }




}
