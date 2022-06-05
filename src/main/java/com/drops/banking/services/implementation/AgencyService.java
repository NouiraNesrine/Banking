package com.drops.banking.services.implementation;

import com.drops.banking.entities.Agency;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Customer;
import com.drops.banking.entities.Supervisor;
import com.drops.banking.repositories.AgencyRepository;
import com.drops.banking.services.IAgencyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgencyService implements IAgencyService {

    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private SupervisorService supervisorService;
    @Override
    public List<Agency> getAgencies() throws Exception{
        List<Agency> agencyList = new ArrayList<Agency>();
        agencyList = agencyRepository.findAll();
        if(agencyList.size()<1){
            throw new Exception("No agency found");
        }
        return agencyList;
    }
    @Override
    public Agency getAgencyById(Long id) throws NotFoundException{
        return agencyRepository.findById(id).orElseThrow(()->
                new NotFoundException("Agency not found, check the id: "+id)
        );
    }
    @Override
    public void createAgency(Agency agency) throws NotFoundException{
        Supervisor supervisor = supervisorService.getSupervisorByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        agency.setSupervisor(supervisor);
        agencyRepository.save(agency);
    }
    @Override
    public void removeAgency(Long id) throws NotFoundException{
        if(agencyRepository.findById(id).isPresent()){
            Agency agency = getAgencyById(id);
            agencyRepository.delete(agency);
        }else {
            throw new NotFoundException("Agency does not exist, wrong id: "+id);
        }
    }
    @Override
    public List<Banker> getAgencyBankers(Long id) throws Exception{
        List<Banker> bankerList = new ArrayList<Banker>();
        if(agencyRepository.findById(id).isPresent()) {
            Agency agency = getAgencyById(id);
            if(agency.getBankerList().isEmpty()) throw new Exception ("No bankers found");
            bankerList = agency.getBankerList();
        }
        return bankerList;
    }
    @Override
    public List<Customer> getAgencyCustomers(Long id) throws Exception{
        List<Customer> customerList = new ArrayList<Customer>();
        if(agencyRepository.findById(id).isPresent()) {
            Agency agency = getAgencyById(id);
            if(agency.getCustomerList().isEmpty()) throw new Exception ("No customer found");
            customerList = agency.getCustomerList();
        }
        return customerList;
    }

}
