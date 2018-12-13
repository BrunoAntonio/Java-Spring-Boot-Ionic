package com.bruno.springboot.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.repositories.ClienteRepository;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	//classe do java que gera valores aleatorios
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente ==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente,newPass);
	}

	//gerar uma senha aleatoria
	private String newPassword() {
		char []vet = new char[10];
		for(int i =0; i<10; i++) {
			vet[i]=randomChar();
		}
		return new String(vet);
	}

	//https://unicode-table.com/pt/#0039
	private char randomChar() {
		int opt = rand.nextInt(3); //gerar um numero inteiro de 0 a 2
		if (opt==0) { //gera um digito
			return (char) (rand.nextInt(10)+48); //para gerar um digito tem de se gerar um numero de 48 a 57(10 digitos) da tabela unicode, então gera-se um numero aleatorio de a a 9 e soma-se ao 48 que é o codigo do 0
		}
		if (opt==1) {//gera letra maiuscula
			return (char) (rand.nextInt(26)+65);//escolher entre 25 letras maiusculas aleatorias+A maiusculo
		}
		else { //gera letra minuscula
			return (char) (rand.nextInt(26)+97);
		}
	}
	
	
	
	

}
