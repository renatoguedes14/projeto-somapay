package com.renato.projetoSomapay.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.renato.projetoSomapay.service.exception.ObjetoNaoEncontradoException;
import com.renato.projetoSomapay.service.exception.ViolacaoIntegridadeDosDadosException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontradoException(ObjetoNaoEncontradoException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(ViolacaoIntegridadeDosDadosException.class)
	public ResponseEntity<ErroPadrao> violacaoIntegridadeDosDadosException(ViolacaoIntegridadeDosDadosException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	public ResponseEntity<ErroPadrao> argumentoMetodoInvalidoException(MethodArgumentNotValidException e) {
		ErroValidacao erro = new ErroValidacao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro na validação. ");
		
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.addErro(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
