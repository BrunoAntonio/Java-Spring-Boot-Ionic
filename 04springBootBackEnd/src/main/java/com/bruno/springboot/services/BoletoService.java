package com.bruno.springboot.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preecherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime()); //data de vencimento 7 dias depois do pedido ser feito
		
	}
	

}
