package com.bruno.springboot.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.dto.ClienteNewDTO;
import com.bruno.springboot.enums.TipoCliente;
import com.bruno.springboot.repositories.ClienteRepository;
import com.bruno.springboot.resources.exception.FieldMessage;
import com.bruno.springboot.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		//Verificação dos numeros do cpfOucnpj
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOucnpj())) {
			list.add(new FieldMessage("cpfOucnpj","CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOucnpj())) {
			list.add(new FieldMessage("cpfOucnpj","CNPJ inválido"));
		}
		
		//Verificaão para não haver emails repetidos na inserção de cliente
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux !=null) {
			list.add(new FieldMessage("email","Email existente"));
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}