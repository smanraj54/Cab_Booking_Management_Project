package com.dal.cabby.profileManagement;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    // Default sender's email credentials
    private static final String senderEmail = "software5408group15@gmail.com";
    private static final String password = "software5408group15123";

    /*
        Static method to create session with getting the email of receiver,
        title and body strings.
     */
    public static void sendEmail(String to, String title, String body)
            throws MessagingException {

        Session session = sessionCreate();
        System.out.println("Sending email to : " + to);

        MimeMessage message = new MimeMessage(session);
        messageBodyPreparation(message, to, title, body);

        Transport.send(message);
        System.out.println("Email Sent Successfully!!!");
    }

    /*
        Creating a proper message using sender's, receiver's mail ids and body
        and title.
     */
    private static void messageBodyPreparation(MimeMessage message, String to,
                                               String title, String body)
            throws MessagingException {

        message.setContent(body, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(title);
    }

    /*
        Creating a session with the connection of Javax.mail
     */
    private static Session sessionCreate() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        System.out.println("Authenticating with Email : " +
                                senderEmail + " " +
                                "and Password : " + password + "");

                        return new PasswordAuthentication(senderEmail,
                                password);
                    }

                });

        return session;
    }

}
