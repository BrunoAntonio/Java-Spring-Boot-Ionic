package com.bruno.springboot.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//gerar a documentação da API
//http://localhost:8080/swagger-ui.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final ResponseMessage m201 = customMessage1();
	private final ResponseMessage m204put = simpleMessage(204, "Atualização ok");
	private final ResponseMessage m204del = simpleMessage(204, "Deleção ok");
	private final ResponseMessage m403 = simpleMessage(403, "Não autorizado");
	private final ResponseMessage m404 = simpleMessage(404, "Não encontrado");
	private final ResponseMessage m422 = simpleMessage(422, "Erro de validação");
	private final ResponseMessage m500 = simpleMessage(500, "Erro inesperado");
	
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				
				.useDefaultResponseMessages(false) //Configurar as mensagens
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m403, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500))
				.select()
				
				.apis(RequestHandlerSelectors.basePackage("com.bruno.springboot.resources")).paths(PathSelectors.any())// vez de todos por default apenas exibir a documentação desse pacote
				.build()
				.apiInfo(apiInfo());
	}

	//Informações sobre a API
	private ApiInfo apiInfo() {
		return new ApiInfo("Execução da API do curso Spring Boot",
				"Esta API foi programada no curso de Spring Boot da Udemy", "Versão 1.0",
				"https://www.udemy.com/terms",
				new Contact("Bruno Cabanas Cardoso","https://www.udemy.com/spring-boot-ionic/", "bruno@hotmail.com"),
				"Programada durante o curso da Udemy de acordo com os seus termos de uso ", "https://www.udemy.com/terms", Collections.emptyList() // Vendor Extensions
		);
	}
	
	//substituir as mensagens padrão no swagger
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
		}
	
	// Response headers personalizados
	private ResponseMessage customMessage1() {
		Map<String, Header> map = new HashMap<>();
		map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
		return new ResponseMessageBuilder()
		.code(201)
		.message("Recurso criado")
		.headersWithDescription(map)
		.build();
		}
	

}