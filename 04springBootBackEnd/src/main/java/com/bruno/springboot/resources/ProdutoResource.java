package com.bruno.springboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bruno.springboot.domain.Produto;
import com.bruno.springboot.dto.ProdutoDTO;
import com.bruno.springboot.resources.uteis.URL;
import com.bruno.springboot.services.ProdutoService;



@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	//procurar por id
	@RequestMapping(value="/{id}", method=RequestMethod.GET)     
	public ResponseEntity<Produto> find(@PathVariable Integer id)  {   
		Produto obj = service.find(id);	
		return ResponseEntity.ok().body(obj);
	}
	
	//consulta personalizada por parte do nome
	//teste no Post http://localhost:8080/produtos/?nome=or&categorias=1,4
	@RequestMapping(method = RequestMethod.GET) 
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,  //os dados colocados na uri são sempre String 
			@RequestParam(value="categorias", defaultValue="") String categorias,  //os dados colocados na uri são sempre String 
			@RequestParam(value="page", defaultValue="0") Integer page,   
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) { 
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded,ids, page,linesPerPage,orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

}
