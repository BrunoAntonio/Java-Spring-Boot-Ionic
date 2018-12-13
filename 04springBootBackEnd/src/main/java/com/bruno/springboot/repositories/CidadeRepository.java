package com.bruno.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.springboot.domain.Cidade;

@Repository  //Integer por causa do id da categoria
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.name") //seleccionar um objecto onde o obj.estado.id é igual ao parametro de @param e ordenadar as cidades pelo nome
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);//parametro estadoID que será passado para a query em :estadoId
	
}
