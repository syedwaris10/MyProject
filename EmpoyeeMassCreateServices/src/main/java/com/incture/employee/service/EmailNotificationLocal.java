package com.incture.employee.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.incture.employee.dto.EmailNotificationDto;

public interface EmailNotificationLocal {

	public void sendEmail(EmailNotificationDto emailDto) throws AddressException, MessagingException;
}
