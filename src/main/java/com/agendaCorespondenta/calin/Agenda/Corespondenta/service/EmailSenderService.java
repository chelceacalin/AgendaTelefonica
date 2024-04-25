package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.controller.ContactController;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

	@Value("${spring.mail.host}")
	private String smtpHost;

	@Value("${spring.mail.port}")
	private String smtpPort;


	public void sendEmail(ContactController.MailTemplate mailTemplate, UserEntity user) {
		try {
			String fromEmail = mailTemplate.fromEmail();
			String password = user.getSmtpEmailPassword();
			String toEmail = mailTemplate.toEmail();

			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.port", smtpPort);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};

			Session session = Session.getInstance(props, auth);
			Transport transport = session.getTransport("smtp");

			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail));
			msg.setReplyTo(InternetAddress.parse(toEmail, false));
			msg.setSubject(mailTemplate.subject(), "UTF-8");
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(mailTemplate.body());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Error sending mail!");
		}
	}
}
