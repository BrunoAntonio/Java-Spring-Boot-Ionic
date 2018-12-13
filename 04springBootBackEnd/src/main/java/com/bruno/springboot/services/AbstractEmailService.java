package com.bruno.springboot.services;



import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine; //retornar o html na forma de string
	
	@Autowired
	private JavaMailSender javaMailSender; //instanciar um objecto MimeMessage
	
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: "+obj.getId() );
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);//substituir pedido do ficheiro html pelo obj da função
		return templateEngine.process("email/confirmacaoPedido", context); //processar o template e retornar o html na forma de string
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
		MimeMessage mm = prepareMimeMessageFromPedido(obj);
		sendHtmlEmail(mm);
		}
		catch (MessagingException e){
			sendOrderConfirmationEmail(obj); //se der excepção envia o email sem html
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true); //atribuir valores à mensagem
		mmh.setTo(obj.getCliente().getEmail()); //email do destinatario
		mmh.setFrom(sender); //email do remetente
		mmh.setSubject("Pedido confirmado! Código: "+obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true); //true para indicar que conteudo é html
		
		return mimeMessage;
	}
	
	@Override //metodo principal para envio de email com nova senha
	public void sendNewPasswordEmail(Cliente cliente,String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente,newPass);
		sendEmail(sm);
	}
	
	//metodo auxiliar para envio de email com nova senha
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente,String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: "+newPass);
		return sm;
	}
	
}
