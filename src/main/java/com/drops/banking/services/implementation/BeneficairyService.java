package com.drops.banking.services.implementation;

import com.drops.banking.entities.Beneficiary;
import com.drops.banking.repositories.BeneficiaryRepository;
import com.drops.banking.services.IBeneficiaryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeneficairyService implements IBeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    @Override
    public List<Beneficiary>  getBeneficairies() throws Exception{
        List<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();
        beneficiaryList = beneficiaryRepository.findAll();
        if(beneficiaryList.size()<1){
            throw new Exception("No beneficiary found");
        }
        return beneficiaryList;
    }
    @Override
    public Beneficiary getBenficiairyByAccountNumber(int accountNumber) throws NotFoundException{
        return beneficiaryRepository.findBeneficiaryByAccountNumber(accountNumber).orElseThrow(()->
                new NotFoundException("No beneficiary with this account number: "+accountNumber));
    }
    @Override
    public Beneficiary getBenficiairyById(Long id) throws NotFoundException{
        return beneficiaryRepository.findById(id).orElseThrow(()->
                new NotFoundException("No beneficiary with this id: "+id));
    }
    @Override
    public void createBeneficiary(Beneficiary beneficiary){
        beneficiaryRepository.save(beneficiary);
    }
    @Override
    public void removeBenificiary(Long id){
        beneficiaryRepository.deleteById(id);
    }

}
