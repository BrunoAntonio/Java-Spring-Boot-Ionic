package com.bruno.springboot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity //anotação do jpa para criar a tabela categoria com os campos id e name
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //.identity funciona com o h2, dependendo do baco de dados utilizado pode ter-se de utilizar outro identificador
	private Integer Id;
	private String nome;
	
	
	@ManyToMany(mappedBy="categorias") //Para utilizar o mapeamento da lista categorias e não ter de escrever a anotação jointable novamente
	private List <Produto> produtos = new ArrayList<>(); //Iniciar a colecção(lista) de produtos
													    //Colecções não entram nos construtores (porque já foi iniciada)
	public Categoria() {
	}

	public Categoria(Integer id, String nome) {
		Id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List <Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List <Produto> produtos) {
		this.produtos = produtos;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	
	
	
	
	
	

}