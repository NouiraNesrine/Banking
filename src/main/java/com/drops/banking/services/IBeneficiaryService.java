package com.drops.banking.services;

import com.drops.banking.entities.Beneficiary;
import javassist.NotFoundException;

import java.util.List;

public interface IBeneficiaryService {
    List<Beneficiary> getBeneficairies() throws Exception;
    Beneficiary getBenficiairyByAccountNumber(int accountNumber) throws NotFoundException;
    Beneficiary getBenficiairyById(Long id) throws NotFoundException;
    void createBeneficiary(Beneficiary beneficiary);
    void removeBenificiary(Long id);
}
