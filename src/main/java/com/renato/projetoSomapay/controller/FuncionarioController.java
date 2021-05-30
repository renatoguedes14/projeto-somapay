package com.renato.projetoSomapay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projetoSomapay.controller.request.FuncionarioRequest;
import com.renato.projetoSomapay.service.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<?> consultarSaldo(@PathVariable final Long numSequencial) {
		return funcionarioService.consultarSaldo(numSequencial);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Valid final FuncionarioRequest request) {
		return funcionarioService.inserir(request);
	}
}
