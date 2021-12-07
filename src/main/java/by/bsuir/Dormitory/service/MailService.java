package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.exception.MyMailException;
import by.bsuir.Dormitory.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("dormitory.bsuir@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation message is sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new MyMailException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

    @Async
    public void sendMessage(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("dormitory.bsuir@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Answer is sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new MyMailException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}
