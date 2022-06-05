package com.drops.banking.services;

import com.drops.banking.entities.Supervisor;
import javassist.NotFoundException;

import java.util.List;

public interface ISupervisorService {
    List<Supervisor> getSupervisors()throws Exception;
    Supervisor getSupervisorById(Long id)throws NotFoundException;
    Supervisor getSupervisorByUsername(String username)throws NotFoundException;
    void createSupervisor(Supervisor supervisor) throws Exception;
    void updateSupervisor(Supervisor supervisor) throws NotFoundException;
    void removeSupervisor(Long id) throws NotFoundException;

}
