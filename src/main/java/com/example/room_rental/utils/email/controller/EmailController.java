package com.example.room_rental.utils.email.controller;

import com.example.room_rental.utils.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail() {
        emailService.sendSimpleEmail("recipient@example.com", "Test Email", "This is a test email body.");
        return "Email sent successfully!";
    }
}
