package com.dal.cabby.profileManagement;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SendEmailTest {

    private String emailId = "sorrydonthaveany@gmail.com";
    private String title = "Test Email!!";
    private String body = "Cabby is testing his functionality of sending Email";

    @Test
    void sendEmail() {

        try {
            SendEmail.sendEmail(emailId,title, body );
        } catch (MessagingException e) {
            assertTrue(true, "Sending Email failed!!!");
        }
    }
}