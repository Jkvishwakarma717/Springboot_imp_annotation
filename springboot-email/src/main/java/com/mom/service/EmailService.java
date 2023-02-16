package com.mom.service;

import com.mom.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(EmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(request.getToEmail());
        message.setText(request.getMessageBody());
        javaMailSender.send(message);
        return "Email SuccessFully Send To : " + request.getToEmail();
    }
    public String sendEmailWithAttachment(EmailRequest request) throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom(sender);
         helper.setTo(new String []{"jeetendrajava22@gmail.com","sandeep.gautam@momagic.com","neelesh.yadav@momagic.com",
        "ajit.kumar@momagic.com"});
       // helper.setTo(request.getToEmails());
        helper.setSubject(request.getSubject());
        helper.setText(request.getMessageBody());

        FileSystemResource file=new FileSystemResource(new File(request.getAttachment()));
        helper.addAttachment(file.getFilename(),file);
        javaMailSender.send(message);
        return "Mail sent Successfully with attachment "+file.getFilename();

    }
}
