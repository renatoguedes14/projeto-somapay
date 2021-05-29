package com.renato.projetoSomapay.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.model.Transacao;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;
import com.renato.projetoSomapay.repository.TransacaoRepository;

@RestController
@RequestMapping(value = "/transacao")
public class TransacaoController {

	@Autowired
	private TransacaoRepository repository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@PostMapping("/realizar")
	public Transacao realizarTransacao(@RequestParam Long empresaNumSequencial,
			@RequestParam Long funcionarioNumSequencial, @RequestParam BigDecimal valor) {
		Optional<Empresa> empresa = empresaRepository.findById(empresaNumSequencial);
		if (empresa != null) {
			Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioNumSequencial);
			if (funcionario != null) {
				Transacao transacao = new Transacao();
				if (transacao.calcularTransacao(funcionario.get(), empresa.get(), valor)) {
					return repository.save(transacao);
				}
			}
		}
		return null;
	}
}
