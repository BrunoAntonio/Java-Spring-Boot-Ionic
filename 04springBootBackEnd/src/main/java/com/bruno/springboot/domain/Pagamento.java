package com.bruno.springboot.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.bruno.springboot.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy=InheritanceType.JOINED) //herança, Singletable=uma tabela para cada tipo de pagamento, joined=uma unica tabela para os 2 tipos de pagamento
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") //para quando inserir um pedido e o respectivo metodo de pagamento o Json mapear o tipo de pagamento e saber se é pagamentoComBoleto ou PagamentoComCartão
public abstract class Pagamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id //porque é uma relação 1 para 1, o pagamento tem o mesmo id do pedido
	private Integer id;
	private Integer estado; //para armazenar internamente o estado do pagamento como numero inteiro(ver enum com numeração de 1,2,3)
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id") 
	@MapsId //porque é uma relação 1 para 1, o pagamento tem o mesmo id do pedido
	private Pedido pedido;

	public Pagamento() {
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		this.id = id;
		this.estado = (estado==null)?null : estado.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
