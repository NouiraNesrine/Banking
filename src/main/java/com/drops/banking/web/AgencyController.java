package com.drops.banking.web;

import com.drops.banking.entities.Agency;
import com.drops.banking.entities.Supervisor;
import com.drops.banking.repositories.AgencyRepository;
import com.drops.banking.services.implementation.AgencyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;

    @GetMapping("/agencies")
    @ResponseStatus(HttpStatus.OK)
    public List<Agency> getAgencies(@RequestParam(name = "id",required = false)Long id) throws Exception {
        return agencyService.getAgencies();
    }
    @GetMapping("/agency")
    @ResponseStatus(HttpStatus.OK)
    public Agency getAgency(@RequestParam(name = "id")Long id) throws NotFoundException {
        return agencyService.getAgencyById(id);
    }
    @PostMapping("/agencies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAgency(@RequestBody Agency agency) throws Exception {
        agencyService.createAgency(agency);
    }
    @DeleteMapping("/agencies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAgency(@PathVariable(name = "id")Long id) throws NotFoundException {
        agencyService.removeAgency(id);
    }
}
