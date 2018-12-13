package com.bruno.springboot.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.bruno.springboot.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("pagamentoComBoleto") //Para quando inserir um pedido o Json mapear a palavra e saber o tipo de pagamento 
public class PagamentoComBoleto extends Pagamento{

	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/HH/yyyy") 
	private Date dataVencimento;
	
	@JsonFormat(pattern="dd/HH/yyyy") 
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento,//construtor utilizando a superclasse
			Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	

}
