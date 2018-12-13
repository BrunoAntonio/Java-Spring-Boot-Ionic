package com.bruno.springboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.springboot.domain.Pedido;
import com.bruno.springboot.services.PedidoService;

//Serviço contem as regras de negocio
//Repository comtem o acesso a dados
//Dominio contem as entidades do negocio
//Resource ou controller comtem a conecção com o front-end(para teste o postman)


@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)     //endpoit: /categorias/id
	public ResponseEntity<?> find(@PathVariable Integer id)  {    //path para passar o id da url para a variavel
		Pedido obj = service.procurar(id);	
		return ResponseEntity.ok().body(obj);

	}

}
