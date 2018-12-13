package com.bruno.springboot.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.springboot.domain.Cliente;

@Repository  //Integer por causa do id da categoria
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	//Procurar um cliente por email
	@Transactional(readOnly=true) //para ser mais rapido, não ser necessária uma transacção com o banco de dados 
	Cliente findByEmail(String email);
	
}
