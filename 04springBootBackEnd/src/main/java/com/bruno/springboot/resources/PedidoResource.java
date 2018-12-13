package com.bruno.springboot.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Pedido> find(@PathVariable Integer id)  {    //path para passar o id da url para a variavel
		Pedido obj = service.find(id);	
		return ResponseEntity.ok().body(obj);

	}
	//Não foi criado um DTO para inserir o objecto pedido
	//Inserir um pedido
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) { 
		obj = service.insert(obj);
									
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); 
																														
		return ResponseEntity.created(uri).build();
	}
	
	//obter apenas os pedidos do cliente logado
	@RequestMapping(method = RequestMethod.GET) 
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,   
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante")String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC")String direction) { 
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
	

}
