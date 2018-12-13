package com.bruno.springboot.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"), //role_admin como o sping exige que se coloque
	CLIENTE(2 , "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer id) { //para armazenar internamente o tipo de cliente como numero inteiro
		if (id == null) {
			return null;
		}
		for (Perfil x : Perfil.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}

	
}
