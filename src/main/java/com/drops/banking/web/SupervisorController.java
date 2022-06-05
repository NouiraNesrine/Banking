package com.drops.banking.web;

import com.drops.banking.entities.Supervisor;
import com.drops.banking.services.implementation.SupervisorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins= "*")
public class SupervisorController {
    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/supervisors")
    @ResponseStatus(HttpStatus.OK)
    public List<Supervisor> getSuperviors(@RequestParam(name = "id",required = false)Long id) throws Exception {
        return supervisorService.getSupervisors();
    }
    @GetMapping("/supervisor")
    @ResponseStatus(HttpStatus.OK)
    public Supervisor getSupervior(@RequestParam(name = "id")Long id) throws NotFoundException {
        return supervisorService.getSupervisorById(id);
    }
    @GetMapping("/supervisor/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Supervisor getSupervior(@PathVariable(name = "username")String username) throws NotFoundException {
        return supervisorService.getSupervisorByUsername(username);
    }
    @PostMapping("/supervisors")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSupervior(@RequestBody Supervisor supervisor) throws Exception {
        supervisorService.createSupervisor(supervisor);
    }
    @PutMapping("/supervisor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSupervior(@PathVariable(name = "id")Long id,@RequestBody(required=false) Supervisor supervisor) throws NotFoundException {
        supervisorService.updateSupervisor(supervisor);
    }
    @DeleteMapping("/supervisor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSupervior(@PathVariable(name = "id")Long id) throws NotFoundException {
        supervisorService.removeSupervisor(id);
    }

}
