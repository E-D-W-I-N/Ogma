package Ogma.service;

public interface MailSender {
    void send(String emailTo, String subject, String msg);
}
