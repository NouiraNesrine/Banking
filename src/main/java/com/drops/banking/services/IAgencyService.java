package com.drops.banking.services;

import com.drops.banking.entities.Agency;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Customer;
import javassist.NotFoundException;

import java.util.List;

public interface IAgencyService {

    List<Agency> getAgencies() throws Exception;
    Agency getAgencyById(Long id) throws NotFoundException;
    void createAgency(Agency agency) throws NotFoundException;
    void removeAgency(Long id) throws NotFoundException;
    List<Banker> getAgencyBankers(Long id) throws Exception;
    List<Customer> getAgencyCustomers(Long id) throws Exception;
}
