package Ogma.service.impl;

import Ogma.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImpl implements MailSender {

    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void send(String emailTo, String subject, String msg) {
        SimpleMailMessage mailMsg = new SimpleMailMessage();

        mailMsg.setFrom(username);
        mailMsg.setTo(emailTo);
        mailMsg.setSubject(subject);
        mailMsg.setSubject(subject);
        mailMsg.setText(msg);

        mailSender.send(mailMsg);
    }
}
