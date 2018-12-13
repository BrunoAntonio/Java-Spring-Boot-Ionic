package com.bruno.springboot.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bruno.springboot.domain.Categoria;
import com.bruno.springboot.dto.CategoriaDTO;
import com.bruno.springboot.services.CategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//Serviço contem as regras de negocio
//Repository comtem o acesso a dados
//Dominio contem as entidades do negocio
//Resource ou controller comtem a conecção com o front-end(para teste o postman)

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	// Obter uma categoria com GET
	@ApiOperation(value="Busca por id")//descrição personalizada na documentação swagger
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // endpoit: /categorias/id
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // path para passar o id da url para a variavel
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

	// Publicar uma categoria com POST no post->body->raw->json e escrever o texto
	// {};
	@PreAuthorize("hasAnyRole('ADMIN')") //apenas admins podem fazer post
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) { //@Valid para validar os dados da CategoriaDTO(NotEmpty e Length) @RequestBody para o objecto categoria ser construtido a partir dos dodos JSON enviados
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);// para seegurar que o obj que vai ser colocado no bancao de dados com um novo
									// id é novo.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); // uri
																														// pega
		return ResponseEntity.created(uri).build();
	}

	// Actualizar categoria com PUT no post->body->raw->json e escrever o texto {};
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id); // para garantir que a categoria actualizada é a categoria para a qual se passou
						// o id
		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	// Apagar categoria com DELETE no post
	@ApiResponses(value = { // Mensagens de resposta específicas na documentação swagger
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente")})	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Obter todas as categorias com GET, usa-se a categoriaDTO porque se usa-se a categoria os produtos pertencentes a ela tambem eram mostrados
	@RequestMapping(method = RequestMethod.GET) 
	public ResponseEntity<List<CategoriaDTO>> findAll() { 
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//Obter só algumas categorias 
	//http://localhost:8080/categorias/page?linesPerPage=3&page=1&direction=DESC -> para testar no post alterar os valores padrão e obter só 3 linhas por pagina, a 2ª pagina e ordenar por oedem decrescente
	@ApiOperation(value="Exibe todas as categorias com paginação")
	@RequestMapping(value = "/page", method = RequestMethod.GET) 
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,   //Se não exibir o parametro page volto para a 1ª pagina(pagina 0)
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, //exibir 24 linhas por padrão
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, //ordenar por nome
			@RequestParam(value="direction", defaultValue="ASC")String direction) { //ASC ascendente e DESC descendente
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	
}
