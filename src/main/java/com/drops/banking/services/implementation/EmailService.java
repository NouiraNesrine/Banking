package com.drops.banking.services.implementation;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Customer;
import com.drops.banking.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    public JavaMailSender javaMailSender;
    @Override
    public void sendEmail(Customer customer) throws MailException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Password Recovery");
        message.setText(
                "Hello,\n This email was generated because you issued a password recovery please do not reply.\n"
                +"username: "+customer.getUsername()+"\n"
                +"password: "+customer.getPassword()+"\n\n"
                +"Best Regards"
        );
        javaMailSender.send(message);
    }
    @Override
    public void meetingEmailConfirmationToCustomer(Customer customer, Banker banker, Availability availability) throws MailException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Password Recovery");
        message.setText(
                "Hello,\n please confirm your meeting with our agent "+banker.getFirstName()+" "+banker.getLastName()+" at "+availability.getStartAt()+" to "+availability.getFinishAt()+"."
                 +"\n\nBest Regards"
        );
        javaMailSender.send(message);
    }
    @Override
    public void meetingEmailConfirmationToAgent(Customer customer, Banker banker, Availability availability) throws MailException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Password Recovery");
        message.setText(
                "Hello,\n you've been scheduled a meeting with our customer "+customer.getFirstName()+" "+customer.getLastName()+" at "+availability.getStartAt()+" to "+availability.getFinishAt()+"."
                        +"\n\nBest Regards"
        );
        javaMailSender.send(message);
    }

}
