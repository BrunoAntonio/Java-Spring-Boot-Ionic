package com.bruno.springboot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bruno.springboot.services.DBService;
import com.bruno.springboot.services.EmailService;
import com.bruno.springboot.services.SmtpEmailService;

//teste da base de dados
@Configuration
@Profile("dev") //Os Beans dentro desta classe só sao activados se o application-tes.properties estiver activo
public class DevConfig {
	
	@Autowired
	private DBService dBService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String stratagy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException { //ParseException por causa da data no metodo instantiateDataBase
		
		
		//Se for diferente de create a base de dados não é novamente criada quando é inicializada
		if (!"create".equals(stratagy)) {
			return false;
		}
		
		dBService.instantiateDataBase();
		return true;
	}
	
	@Bean 
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
