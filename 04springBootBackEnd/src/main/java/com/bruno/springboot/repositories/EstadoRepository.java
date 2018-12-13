package com.bruno.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.springboot.domain.Estado;

@Repository  //Integer por causa do id da categoria
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
	
}
