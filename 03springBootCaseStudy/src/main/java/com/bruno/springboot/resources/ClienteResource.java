package com.bruno.springboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.services.ClienteService;

//Serviço contem as regras de negocio
//Repository comtem o acesso a dados
//Dominio contem as entidades do negocio
//Resource ou controller comtem a conecção com o front-end(para teste o postman)


@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)     //endpoit: /categorias/id
	public ResponseEntity<?> find(@PathVariable Integer id)  {    //path para passar o id da url para a variavel
		Cliente obj = service.procurar(id);	
		return ResponseEntity.ok().body(obj);

	}

}
