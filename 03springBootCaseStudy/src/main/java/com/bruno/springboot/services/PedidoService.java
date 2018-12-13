package com.bruno.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.Pedido;
import com.bruno.springboot.repositories.PedidoRepository;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	/*
	 * Sem tratamento de excepção 
	 * public Pedido procurar(Integer id){
	 * Optional<Pedido> obj = repo.findById(id); 
	 * return obj.orElse(null); }
	 */

	//Com tratamento de excepção
	public Pedido procurar(Integer id){
        Optional<Pedido> optional = repo.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
	

}
