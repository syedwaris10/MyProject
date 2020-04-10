package com.incture.employee.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.incture.employee.dto.EmailNotificationDto;

@Service
public class EmailNotification implements EmailNotificationLocal {

	@Override
	public void sendEmail(EmailNotificationDto emailDto) throws AddressException, MessagingException {
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailDto.getFrom()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDto.getTo()));
		message.setSubject(emailDto.getSubject());
		message.setText(emailDto.getBody());
		Transport.send(message);
	}

}
