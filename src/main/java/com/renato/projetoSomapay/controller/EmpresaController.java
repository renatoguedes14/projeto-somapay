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

import com.renato.projetoSomapay.form.EmpresaForm;
import com.renato.projetoSomapay.form.TransacaoForm;
import com.renato.projetoSomapay.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaController {

	@Autowired
	private EmpresaService service;

	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<?> consultarSaldo(@PathVariable Long numSequencial) {
		return service.consultarSalto(numSequencial);
	}

	@Transactional
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Valid EmpresaForm form) {
		return service.inserir(form);
	}
	
	@Transactional
	@PostMapping("/transferir")
	public ResponseEntity<?> transferir(@RequestBody @Valid TransacaoForm form) {
		return service.transferir(form);
	}
}
