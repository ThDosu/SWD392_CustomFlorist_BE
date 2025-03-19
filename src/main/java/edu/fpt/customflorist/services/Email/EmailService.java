package edu.fpt.customflorist.services.Email;

import edu.fpt.customflorist.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    private String verificationLinkPage = "http://localhost:8080/custom-florist/api/v1/users/verify";
    private String emailSendFrom = "nam022831@gmail.com";

    @Override
    public void sendVerificationEmail(User user) throws Exception {
        String verificationLink = verificationLinkPage + "?code=" + user.getVerificationCode();
        String subject = "Verify your account";
        String content = "Hi " + user.getName() + ",\n\n"
                + "Please click on the following link to verify your account:\n"
                + verificationLink + "\n\n"
                + "If you do not have an account, please ignore this email.";

        sendEmail(user.getEmail(), subject, content);
    }

    @Override
    public void sendEmail(String to, String subject, String content) throws Exception {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailSendFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email");
        }
    }
}

