package br.luciano.email.controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.luciano.email.dtos.EmailDto;
import br.luciano.email.models.EmailModel;
import br.luciano.email.services.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {
	
	@InjectMocks
	private EmailController emailController;
	
	@Mock
	EmailService emailService;
	
	private EmailDto emailDto;
	private EmailModel emailModel;
	
	@BeforeEach
	void setup() {
		emailDto = new EmailDto();
		emailDto.setOwnerRef("receber@email.com");
		emailDto.setEmailFrom("luciano.s.mend@gmail.com");
		emailDto.setEmailTo("luciano_men@live.com");
		emailDto.setSubject("assunto");
		emailDto.setText("corpo do e-mail para ser enviado");
		
		emailModel = new EmailModel();
		BeanUtils.copyProperties(emailDto, emailModel);
	}
	
	
	@Test
	void deveSendingEmail() {
		when(emailService.sendEmail(emailModel)).thenReturn(emailModel);
		
		var response = assertDoesNotThrow(() -> emailController.sendingEmail(emailDto));
		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(emailModel), response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
