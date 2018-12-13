package com.bruno.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.springboot.domain.Produto;

@Repository  //Integer por causa do id da categoria
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
	
}
