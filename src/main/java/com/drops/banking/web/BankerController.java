package com.drops.banking.web;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Meeting;
import com.drops.banking.services.implementation.BankerService;
import com.drops.banking.services.implementation.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class BankerController {
    @Autowired
    private BankerService bankerService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/supervisor/bankers")
    @ResponseStatus(HttpStatus.OK)
    public List<Banker> getBankers(@PathVariable(name="id", required=false) Long id) throws Exception{
        return  bankerService.getBankers();
    }
    @GetMapping("/banker/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Banker getBankerByUsername(@PathVariable(name="username") String username) throws Exception {
        return bankerService.getBankerByUsername(username);
    }
    @PostMapping("/supervisor/banker")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBanker(@RequestBody Banker banker)  throws Exception{
        bankerService.createBanker(banker);
    }
    @PutMapping("/supervisor/banker/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBanker(@PathVariable Long id , @RequestBody(required=false) Banker banker)  throws Exception {
        bankerService.updateBanker(banker);
    }
    @DeleteMapping("/supervisor/banker/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBanker(@PathVariable Long id) throws Exception{
        bankerService.removeBanker(id);
    }
    @PostMapping("/banker/addAvailability")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAvailability(@RequestBody Availability availability) throws Exception {
        bankerService.createAvailability(availability);
    }
    @GetMapping("/banker/availability")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Availability> getCAvailabilities() throws Exception{
        return bankerService.getAvailabilities();
    }
    @GetMapping("/banker/meeting")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Meeting> getMeetings() throws Exception{
        return bankerService.getBankerByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getMeetingList();
    }
    @PutMapping("/banker/suspend/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void suspend(@PathVariable Long id) throws NotFoundException {
        bankerService.suspendCustomerActivities(id);
    }
}
