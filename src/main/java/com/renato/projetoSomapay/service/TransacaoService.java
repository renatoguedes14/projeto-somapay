package com.renato.projetoSomapay.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;

@Service
@Transactional
public class TransacaoService {

	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	FuncionarioRepository funcionarioRepository;

	public Empresa sacar(final Long numSequencial, final BigDecimal valor) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(numSequencial);
		Empresa empresa = empresaOpt.get();
		empresa.setSaldoAtual(empresaOpt.get().getSaldoAtual());
		if(valor.compareTo(BigDecimal.ZERO) == 1 && valor.compareTo(empresa.getSaldoAtual()) <= 0) {
			empresa.setSaldoAtual(empresa.getSaldoAtual().subtract(valor));
			return empresa;
		}
		return null;
	}
	
	public Funcionario depositar(final Long numSequencial, final BigDecimal valor) {
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(numSequencial);
		Funcionario funcionario = funcionarioOpt.get();
		funcionario.setSaldoAtual(funcionarioOpt.get().getSaldoAtual());
		if (valor.compareTo(BigDecimal.ZERO) == 1) {
			funcionario.setSaldoAtual(funcionario.getSaldoAtual().add(valor));
			return funcionario;
		}
		return null;
	}
}
