package com.drops.banking.services.implementation;

import com.drops.banking.entities.Supervisor;
import com.drops.banking.repositories.SupervisorRepository;
import com.drops.banking.services.ISupervisorService;
import com.drops.banking.services.implementation.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SupervisorService implements ISupervisorService {
    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public List<Supervisor> getSupervisors()throws Exception {
        List<Supervisor> supervisors = new ArrayList<Supervisor>();
        supervisors = supervisorRepository.findAll();
        if(supervisors.size()<1) {
            throw new Exception("Couldn't find any supervisor");
        }
        return supervisors;
    }
    @Override
    public Supervisor getSupervisorById(Long id)throws NotFoundException {
        return  supervisorRepository.findById(id).orElseThrow(()->
            new NotFoundException("Supervisor does not exist, wrong id: "+id)
                );
    }
    @Override
    public Supervisor getSupervisorByUsername(String username)throws NotFoundException {
        return  supervisorRepository.findSupervisorByUsername(username).orElseThrow(()->
                new NotFoundException("Supervisor does not exist, wrong username: "+username)
        );
    }
    @Override
    public void createSupervisor(Supervisor supervisor) throws Exception{
        if(supervisorRepository.findByCin(supervisor.getCin()).isPresent()){
            throw new Exception("This supervisor already exists please check again");
        }
        if(supervisor.getPassword()==null && supervisor.getPassword().length()<1){
            throw new Exception("No valid password");
        }else{
            supervisor.setPassword(new BCryptPasswordEncoder().encode(supervisor.getPassword()));
        }
        supervisor.setCreationDate(new Date());
        supervisorRepository.save(supervisor);
    }
    @Override
    public void updateSupervisor(Supervisor supervisor) throws NotFoundException{
        if(supervisorRepository.findById(supervisor.getId()).isPresent()){
            Supervisor toBeUpdtaedSupervisor = getSupervisorById(supervisor.getId());
            toBeUpdtaedSupervisor.setCin(supervisor.getCin());
            toBeUpdtaedSupervisor.setEmail(supervisor.getEmail());
            toBeUpdtaedSupervisor.setBirthday(supervisor.getBirthday());
            toBeUpdtaedSupervisor.setPhone(supervisor.getPhone());
            toBeUpdtaedSupervisor.setAddress(supervisor.getAddress());
            toBeUpdtaedSupervisor.setUsername(supervisor.getUsername());
            toBeUpdtaedSupervisor.setSex(supervisor.getSex());
            toBeUpdtaedSupervisor.setProfileStatus(supervisor.getProfileStatus());
            supervisorRepository.save(toBeUpdtaedSupervisor);
        }else {
            throw new NotFoundException("Supervisor does not exist, wrong id: "+supervisor.getId());
        }
    }
    @Override
    public void removeSupervisor(Long id) throws NotFoundException{
        if(supervisorRepository.findById(id).isPresent()){
            Supervisor supervisor = getSupervisorById(id);
            supervisorRepository.delete(supervisor);
        }else {
            throw new NotFoundException("Supervisor does not exist, wrong id: "+id);
        }

    }
}
