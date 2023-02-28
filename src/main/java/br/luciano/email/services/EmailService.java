package br.luciano.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.luciano.email.enums.StatusEmail;
import br.luciano.email.models.EmailModel;
import br.luciano.email.repositories.EmailRepository;

@Service
public class EmailService {

	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;

	public EmailModel sendEmail(EmailModel emailModel) {
		//Amazon SES (Simple Email Service)
		emailModel.setSendDateEmail(LocalDateTime.now());
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailModel.getEmailFrom());
			message.setTo(emailModel.getEmailTo());
			message.setSubject(emailModel.getSubject());
			message.setText(emailModel.getText());
			emailSender.send(message);
			
			emailModel.setStatusEmail(StatusEmail.SENT);
		} catch (MailException e) {
			emailModel.setStatusEmail(StatusEmail.ERROR);
		} finally {
			return emailRepository.save(emailModel);
		}
	}
}
