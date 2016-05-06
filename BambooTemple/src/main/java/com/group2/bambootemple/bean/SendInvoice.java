package com.group2.bambootemple.bean;

import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * Here is a starter class for phase 1. It sends and receives basic email that
 * consists of a to, from, subject, text, html, attachment and embedded image.
 *
 * @author Marjorie Morales
 *
 */
public class SendInvoice {

    /**
     * Basic send routine for this sample. It does not handle CC, BCC,
     * attachments or other information.
     *
     * @param mailBean
     * @return
     */
    public final static String sendEmail(final MailBean mailBean) {

        // Create an SMTP server object
        SmtpServer<?> smtpServer = SmtpSslServer.create("smtp.gmail.com")
                .authenticateWith("sendmarjoriemorales@gmail.com", "senddawson");
        // Do not use the fluent type because ArrayLists need to be processed
        // from the bean
        Email email = Email.create();

        email.from("sendmarjoriemorales@gmail.com");

        for (String emailAddress : mailBean.getTo()) {
            email.to(emailAddress);
        }

        email.subject(mailBean.getSubject());

        // Sending mail text 
        email.addText(mailBean.getTextMessageField());

        //Sending mail html format.
        email.addHtml(mailBean.getHtmlMessageField());

        // embed picture
        for (EmailAttachment embed : mailBean.getEmbed()) {
            email.embed(embed);
        }

        // Sending an attachment
        for (EmailAttachment attach : mailBean.getAttachment()) {
            email.attach(attach);
        }

        // A session is the object responsible for communicating with the server
        SendMailSession session = smtpServer.createSession();

        // Like a file we open the session, send the message and close the
        // session
        session.open();
        String messageId = session.sendMail(email);
        session.close();
        return messageId;
    }
}
