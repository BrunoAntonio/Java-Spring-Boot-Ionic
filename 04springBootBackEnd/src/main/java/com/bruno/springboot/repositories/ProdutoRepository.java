package com.bruno.springboot.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.springboot.domain.Categoria;
import com.bruno.springboot.domain.Produto;

@Repository  //Integer por causa do id da categoria
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
	//consulta personalizada por parte do nome (%:nome% pode ter alguma letra antes ou depois do % )
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias ") //consulta personalizada
	Page<Produto> search (@Param("nome") String nome,@Param("categorias") List<Categoria> categorias,Pageable pageRequest);//@param para passar %:nome% para "nome" e :categorias para "categorias"
	
	
	//https://docs.spring.io/spring-data/jpa/docs/2.1.2.RELEASE/reference/html/ tabela 3
	//consulta com metodo do spring por parte do nome
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn (String nome,List<Categoria> categorias,Pageable pageRequest);
	
}
