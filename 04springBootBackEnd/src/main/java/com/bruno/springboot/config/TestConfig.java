package com.bruno.springboot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bruno.springboot.services.DBService;
import com.bruno.springboot.services.EmailService;
import com.bruno.springboot.services.MockEmailService;

//teste da base de dados
@Configuration
@Profile("test") //Os Beans dentro desta classe só sao activados se o application-test.properties estiver activo
public class TestConfig {
	
	@Autowired
	private DBService dBService;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException { //ParseException por causa da data no metodo instantiateDataBase
		
		dBService.instantiateDataBase();
		return true;
	}
	
	@Bean 
	public EmailService emailService() {
		return new MockEmailService();
	}

}