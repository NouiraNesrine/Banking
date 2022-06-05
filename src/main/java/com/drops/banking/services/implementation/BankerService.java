package com.drops.banking.services.implementation;

import com.drops.banking.entities.*;
import com.drops.banking.enums.ProfileStatus;
import com.drops.banking.repositories.AvailabilityRepository;
import com.drops.banking.repositories.BankerRepository;
import com.drops.banking.repositories.CustomerRepository;
import com.drops.banking.services.IBankerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BankerService implements IBankerService {

    @Autowired
    private BankerRepository bankerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Override
    public List<Banker> getBankers() throws Exception{
        List<Banker> bankerList = new ArrayList<Banker>();
        bankerList = bankerRepository.findAll();
        if(bankerList.size()<1){
            throw new Exception("No banker found");
        }
        return bankerList;
    }
    @Override
    public Banker getBankerById(Long id) throws NotFoundException{
        return bankerRepository.findById(id).orElseThrow(()->
                 new NotFoundException("No banker found with this id: "+id)
        );
    }
    @Override
    public Banker getBankerByUsername(String username)throws NotFoundException {
        return  bankerRepository.findBankerByUsername(username).orElseThrow(()->
                new NotFoundException("Supervisor does not exist, wrong username: "+username)
        );
    }
    @Override
    public void createBanker(Banker banker) throws Exception{
        if(bankerRepository.findByCin(banker.getCin()).isPresent()){
            throw new Exception("This banker already exists please check again");
        }
        if(banker.getPassword()==null && banker.getPassword().length()<1){
            throw new Exception("No valid password");
        }else{
            banker.setPassword(new BCryptPasswordEncoder().encode(banker.getPassword()));
        }
        banker.setCreationDate(new Date());
        bankerRepository.save(banker);
    }
    @Override
    public void updateBanker(Banker banker) throws NotFoundException{
        if(bankerRepository.findById(banker.getId()).isPresent()){
            Banker toBeUpdatedBanker = getBankerById(banker.getId());
            toBeUpdatedBanker.setCin(banker.getCin());
            toBeUpdatedBanker.setEmail(banker.getEmail());
            toBeUpdatedBanker.setBirthday(banker.getBirthday());
            toBeUpdatedBanker.setPhone(banker.getPhone());
            toBeUpdatedBanker.setAddress(banker.getAddress());
            toBeUpdatedBanker.setUsername(banker.getUsername());
            toBeUpdatedBanker.setSex(banker.getSex());
            toBeUpdatedBanker.setProfileStatus(banker.getProfileStatus());
            bankerRepository.save(toBeUpdatedBanker);
        }else {
            throw new NotFoundException("banker does not exist, wrong id: "+banker.getId());
        }
    }
    @Override
    public void removeBanker(Long id) throws NotFoundException{
        if(bankerRepository.findById(id).isPresent()){
            Banker banker = getBankerById(id);
            bankerRepository.delete(banker);
        }else {
            throw new NotFoundException("Banker does not exist, wrong id: "+id);
        }

    }
    @Override
    public void suspendCustomerActivities(Long id) throws NotFoundException{
        Customer customerTobeSuspended = customerRepository.findById(id).orElseThrow(()->
                new NotFoundException("Customer does not exist, wrong id: "+id)
        );
       if(customerTobeSuspended!= null){
           customerTobeSuspended.setProfileStatus(ProfileStatus.INACTIVE);
           customerRepository.save(customerTobeSuspended);
       }
    }
    @Override
    public void createAvailability(Availability availability) throws Exception{
        Banker banker = getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        banker.getAvailabilityList().add(availability);
        availability.setBanker(banker);
        bankerRepository.save(banker);
        availabilityRepository.save(availability);
    }
    @Override
    public List<Meeting> getMeetings() throws NotFoundException {
        return getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getMeetingList();
    }
    @Override
    public List<Availability> getAvailabilities() throws NotFoundException {
        return getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getAvailabilityList();
    }



}
