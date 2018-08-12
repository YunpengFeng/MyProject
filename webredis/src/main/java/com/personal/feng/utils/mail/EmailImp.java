package com.personal.feng.utils.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailImp implements Email {
    private JavaMailSender javaMailSender;

    private String systemEmail;

    @Override
    public void sendMail(String to, String subject, String htmlText) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(msg);

        try {
            msgHelper.setFrom(systemEmail);
            msgHelper.setTo(to);
            msgHelper.setSubject(subject);
            msgHelper.setText(htmlText, false);
            javaMailSender.send(msg);
            System.out.println("..............................................邮件发送成功..............................................");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("..............................................邮件发送失败..............................................");
        }


    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }
}
