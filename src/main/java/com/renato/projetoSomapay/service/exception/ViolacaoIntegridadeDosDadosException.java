package com.renato.projetoSomapay.service.exception;

public class ViolacaoIntegridadeDosDadosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ViolacaoIntegridadeDosDadosException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViolacaoIntegridadeDosDadosException(String message) {
		super(message);
	}

}
