package com.bruno.springboot.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.bruno.springboot.services.exceptions.AuthorizationException;
import com.bruno.springboot.services.exceptions.DataIntegrityException;
import com.bruno.springboot.services.exceptions.FileException;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e ,HttpServletRequest request){
		
		StandardError err= new StandardError(System.currentTimeMillis() ,HttpStatus.BAD_REQUEST.value(),"Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	//tratamento do erro da introdução de dados na anotação @validation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e ,HttpServletRequest request){
		
		ValidationError err= new ValidationError(System.currentTimeMillis() ,HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validação", e.getMessage(), request.getRequestURI());
		
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	//tratamento do erro do acesso negado para o tipo de perfil de utilizador
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e ,HttpServletRequest request){
		
		StandardError err= new StandardError(System.currentTimeMillis() ,HttpStatus.FORBIDDEN.value(),"Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	//tratamento do erro do para envio de fotografias
		@ExceptionHandler(FileException.class)
		public ResponseEntity<StandardError> file(AuthorizationException e ,HttpServletRequest request){
			
			StandardError err= new StandardError(System.currentTimeMillis() ,HttpStatus.BAD_REQUEST.value(),"Erro de arquivo", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
		
		@ExceptionHandler(AmazonServiceException.class)
		public ResponseEntity<StandardError> amazonService(AmazonServiceException e ,HttpServletRequest request){
			
			HttpStatus code = HttpStatus.valueOf(e.getErrorCode()); //pegar o codigo que vem da AmazonServiceException e transforma para um objecto do tipo httpStatus
			StandardError err= new StandardError(System.currentTimeMillis() ,code.value(),"Erro Amazon Service", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(code).body(err);
		}
		
		@ExceptionHandler(AmazonClientException.class)
		public ResponseEntity<StandardError> amazonClient(AmazonClientException e ,HttpServletRequest request){
			
			StandardError err= new StandardError(System.currentTimeMillis() ,HttpStatus.BAD_REQUEST.value(),"Erro Amazon Client", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
		
		@ExceptionHandler(AmazonS3Exception.class)
		public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e ,HttpServletRequest request){
			
			StandardError err= new StandardError(System.currentTimeMillis() ,HttpStatus.BAD_REQUEST.value(),"Erro S3", e.getMessage(), request.getRequestURI());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		}
	
}
