package com.bruno.springboot.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore //para não serializar o pedido nem o produto a partir o ItemPedido
	@EmbeddedId //Id da classe embutido numa classe auxiliar
	private ItemPedidoPK id= new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preço;
	
	public ItemPedido() {
	}

	//em vez de acessar em primeiro o id e depois dentro dele o pedido e produto, introduz esses variaveis no construtor
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preço) {
		id.setPedido(pedido);
		id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preço = preço;
	}

	@JsonIgnore//tudo o que começa por get é serializado se não se utilizar a anotação, enteão tem de se ignorar os pedidos e produtos para naão entrar num loop de serialização entre estas 2 entidades 
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreço() {
		return preço;
	}

	public void setPreço(Double preço) {
		this.preço = preço;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
