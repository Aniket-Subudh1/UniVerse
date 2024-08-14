package com.cms.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class GEmailSender {

    public boolean sendEmail(String to, String from, String subject, String text) {
        boolean flag = false;

        // SMTP configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String username = "lvdasarkar";
        String password = "xteidlzidlcaribj"; // Use an app-specific password

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);

            // Send the email
            Transport.send(message);
            flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return flag;
    }
}
