package com.bruno.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.repositories.ClienteRepository;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	/*
	 * Sem tratamento de excepção 
	 * public Cliente procurar(Integer id){
	 * Optional<Cliente> obj = repo.findById(id); 
	 * return obj.orElse(null); }
	 */

	//Com tratamento de excepção
	public Cliente procurar(Integer id){
        Optional<Cliente> optional = repo.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
	

}
