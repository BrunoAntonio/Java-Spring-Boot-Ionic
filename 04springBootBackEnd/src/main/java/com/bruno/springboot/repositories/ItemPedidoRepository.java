package com.bruno.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.springboot.domain.ItemPedido;

@Repository  //Integer por causa do id da categoria
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

	
	
}
