package com.renato.projetoSomapay.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projetoSomapay.form.FuncionarioForm;
import com.renato.projetoSomapay.service.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService service;
	
	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<?> consultarSaldo(@PathVariable Long numSequencial) {
		return service.consultarSaldo(numSequencial);
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Valid FuncionarioForm form) {
		return service.inserir(form);
	}
}
