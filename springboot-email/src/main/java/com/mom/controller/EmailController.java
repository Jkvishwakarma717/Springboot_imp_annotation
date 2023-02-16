package com.mom.controller;

import com.mom.dto.EmailRequest;
import com.mom.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    @Autowired
    private EmailService service;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest request) {
        return service.sendSimpleMail(request);
    }

    //send mail with attachment
    @PostMapping("/sendAttachment")
    public String sendEmailWithAttachment(@RequestBody EmailRequest request) throws MessagingException {
        return service.sendEmailWithAttachment(request);
    }
}