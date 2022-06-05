package com.drops.banking.services;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Customer;
import org.springframework.mail.MailException;

public interface IEmailService {
    void sendEmail(Customer customer) throws MailException;
    void meetingEmailConfirmationToCustomer(Customer customer, Banker banker, Availability availability) throws MailException;
    void meetingEmailConfirmationToAgent(Customer customer, Banker banker, Availability availability) throws MailException;
}
