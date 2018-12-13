package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidades.Pessoa;

public interface PessoasRep extends JpaRepository <Pessoa, Long>{

}
