package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

	final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	String fromEmail;

	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(to);
		message.setText(body);
		message.setSubject(subject);

		javaMailSender.send(message);
		log.info("Email sent successfully!");
	}


}
