package com.drops.banking.services;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Meeting;
import javassist.NotFoundException;

import java.util.List;

public interface IBankerService {
    List<Banker> getBankers() throws Exception;
    Banker getBankerById(Long id) throws NotFoundException;
    Banker getBankerByUsername(String username)throws NotFoundException;
    void createBanker(Banker banker) throws Exception;
    void updateBanker(Banker banker) throws NotFoundException;
    void removeBanker(Long id) throws NotFoundException;
    void suspendCustomerActivities(Long id) throws NotFoundException;
    void createAvailability(Availability availability) throws Exception;
    List<Meeting> getMeetings() throws NotFoundException;
    List<Availability> getAvailabilities() throws NotFoundException;
}
