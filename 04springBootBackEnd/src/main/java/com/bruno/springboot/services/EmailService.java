package com.bruno.springboot.services;



import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.domain.Pedido;

public interface EmailService {
	
	//email normal
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
	//versão html do email
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	
	//recuperar password
	void sendNewPasswordEmail(Cliente cliente,String newPass);

}
