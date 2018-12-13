package com.bruno.springboot.resources.uteis;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	
//para ler string na uri com espaços em branco de "tv led" passar para "tv%20led"	
	public static String decodeParam(String s ) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	//Método para passar do string categorias=1,2,3 para um lista de numeros inteiros
	public static List<Integer> decodeIntList(String s){
		String[] vet =s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		//return Arrays.asList(s.split(",")).stream().map(x->Integer.parseInt(x)).collect(Collectors.toList());
	}

	


}
