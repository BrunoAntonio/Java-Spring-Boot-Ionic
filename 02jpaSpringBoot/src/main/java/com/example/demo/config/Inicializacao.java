package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.demo.entidades.Pessoa;
import com.example.demo.repositorio.PessoasRep;

@Component
public class Inicializacao implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	PessoasRep pessoasRep;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Pessoa p1 = new Pessoa(null, "Bruno", "bruno@gmail.com");
		Pessoa p2 = new Pessoa(null, "Sara", "sara@gmail.com");
		Pessoa p3 = new Pessoa(null, "Tânia", "tania@gmail.com");
		
		
		pessoasRep.save(p1);
		pessoasRep.save(p2);
		pessoasRep.save(p3);
		
		pessoasRep.delete(p3);
		

		System.out.println(p1);
		System.out.println("Terminado");

		

	}

}
