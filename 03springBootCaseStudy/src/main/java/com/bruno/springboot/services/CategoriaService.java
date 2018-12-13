package com.bruno.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.Categoria;
import com.bruno.springboot.repositories.CategoriaRepository;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	/*
	 * Sem tratamento de excepção 
	 * public Categoria procurar(Integer id){
	 * Optional<Categoria> obj = repo.findById(id); 
	 * return obj.orElse(null); }
	 */

	//Com tratamento de excepção
	public Categoria procurar(Integer id){
        Optional<Categoria> optional = repo.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
	

}
