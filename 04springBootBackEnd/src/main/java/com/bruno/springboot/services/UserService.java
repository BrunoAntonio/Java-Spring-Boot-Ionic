package com.bruno.springboot.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bruno.springboot.security.UserSS;

public class UserService {

	// metodo para retornar o user que está logado
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {// pode ocurrer um erro se por exemplo não houver um user logado e tentar-se
								// fazer o cast para userss
			return null;
		}
	}
}
