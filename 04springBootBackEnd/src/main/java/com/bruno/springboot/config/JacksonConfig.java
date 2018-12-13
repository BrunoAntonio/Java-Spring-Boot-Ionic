package com.bruno.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.bruno.springboot.domain.PagamentoComBoleto;
import com.bruno.springboot.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

//Classe de configuração para registrar as subclasses de pagamento na inserção de um pedido
//Codigo padrão onde apenas muda as subclasses a registrar, neste projecto apenas existem duas
@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}