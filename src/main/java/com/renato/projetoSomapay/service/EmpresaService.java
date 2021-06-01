package com.renato.projetoSomapay.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.controller.request.TransacaoRequest;
import com.renato.projetoSomapay.dto.EmpresaDTO;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;
import com.renato.projetoSomapay.service.exception.ViolacaoIntegridadeDosDadosException;
import com.renato.projetoSomapay.service.exception.ObjetoNaoEncontradoException;

@Service
@Transactional
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private TransacaoService transacaoService;

	public Empresa buscarEmpresa(final Long numSequencial) {
		Optional<Empresa> empresa = empresaRepository.findById(numSequencial);
		return empresa.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado." ));
	}

	public List<Empresa> buscarTodos() {
		return empresaRepository.findAll();
	}

	public Empresa inserir(final EmpresaDTO empresaDto) {
		if (findByCnpj(empresaDto) != null) {
			throw new ViolacaoIntegridadeDosDadosException("Empresa já cadastrada na base de dados. ");
		}
		return empresaRepository.save(new Empresa(empresaDto.getNome(), empresaDto.getCnpj(), empresaDto.getEndereco(),
				empresaDto.getSaldoAtual()));
	}

	public Empresa atualizarDados(final Long numSequencial, @Valid final EmpresaDTO empresaDto) {
		Empresa empresa = buscarEmpresa(numSequencial);

		if (findByCnpj(empresaDto) != null && findByCnpj(empresaDto).getNumSequencial() != numSequencial) {
			throw new ViolacaoIntegridadeDosDadosException("CNPJ já cadastrado na base de dados em outra empresa. ");
		}
		empresa.setNome(empresaDto.getNome());
		empresa.setCnpj(empresaDto.getCnpj());
		empresa.setEndereco(empresaDto.getEndereco());
		empresa.setSaldoAtual(empresaDto.getSaldoAtual());
		return empresaRepository.save(empresa);
	}

	public void remover(final Long numSequencial) {
		Empresa empresa = buscarEmpresa(numSequencial);
		if (empresa.getFuncionariosList().size() > 0) {
			throw new ViolacaoIntegridadeDosDadosException("A empresa possui um ou mais funcionários vinculados a ela. ");
		}
		empresaRepository.delete(empresa);
	}

	public Empresa consultarSaldo(final Long numSequencial) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(numSequencial);
		if (empresaOpt.isPresent()) {
			return empresaOpt.get();
		}
		throw new ObjetoNaoEncontradoException("Objeto não encontrado. ");
	}

	public void transferir(final TransacaoRequest request) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(request.getEmpresaNumSequencial());
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.getByCpf(request.getCpfFuncionario());

		if (!empresaOpt.isPresent()) {
			throw new ObjetoNaoEncontradoException("Empresa não encontrada. ");
		}

		if (!funcionarioOpt.isPresent()) {
			throw new ObjetoNaoEncontradoException("Funcionario não encontrado. ");
		}

		Empresa empresa = empresaOpt.get();
		Funcionario funcionario = funcionarioOpt.get();

		if (request.getValorTransacao().compareTo(empresa.getSaldoAtual()) == 1) {
			throw new ViolacaoIntegridadeDosDadosException("Saldo insuficiente. ");
		}

		transacaoService.sacar(empresa.getNumSequencial(), request.getValorTransacao());
		transacaoService.depositar(funcionario.getNumSequencial(), request.getValorTransacao());

		empresaRepository.save(empresa);
		funcionarioRepository.save(funcionario);

	}
	
	private Empresa findByCnpj(final EmpresaDTO empresaDto) {
		Empresa empresa = empresaRepository.findByCnpj(empresaDto.getCnpj());
		if (empresa != null) {
			return empresa;
		}
		return null;
	}
}
