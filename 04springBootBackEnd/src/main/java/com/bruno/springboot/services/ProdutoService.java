package com.bruno.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.Categoria;
import com.bruno.springboot.domain.Produto;
import com.bruno.springboot.repositories.CategoriaRepository;
import com.bruno.springboot.repositories.ProdutoRepository;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	

	/*
	 * rocurar produto por id sem tratamento de excepção 
	 * public Produto procurar(Integer id){
	 * Optional<Produto> obj = repo.findById(id); 
	 * return obj.orElse(null); }
	 */

	//procurar produto por id com tratamento de excepção
	public Produto find(Integer id){
        Optional<Produto> optional = repo.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }
	
	//procurar produto por nome
	public Page <Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids); 
		//return repo.search(nome, categorias, pageRequest);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
