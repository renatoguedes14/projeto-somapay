package com.renato.projetoSomapay.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.controller.request.TransacaoRequest;
import com.renato.projetoSomapay.dto.EmpresaDTO;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;
import com.renato.projetoSomapay.service.exception.DataIntegrityViolationException;
import com.renato.projetoSomapay.service.exception.ObjectNotFoundException;

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
		return empresa.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Código: " + numSequencial + "Tipo: " + Empresa.class.getName()));
	}

	public List<Empresa> buscarTodos() {
		return empresaRepository.findAll();
	}

	public Empresa inserir(final EmpresaDTO empresaDto) {
		if (findByCnpj(empresaDto) != null) {
			throw new DataIntegrityViolationException("Empresa já cadastrada na base de dados. ");
		}
		return empresaRepository.save(new Empresa(empresaDto.getNome(), empresaDto.getCnpj(), empresaDto.getEndereco(),
				empresaDto.getSaldoAtual()));
	}

	public Empresa atualizarDados(final Long numSequencial, @Valid final EmpresaDTO empresaDto) {
		Empresa empresa = buscarEmpresa(numSequencial);

		if (findByCnpj(empresaDto) != null && findByCnpj(empresaDto).getNumSequencial() != numSequencial) {
			throw new DataIntegrityViolationException("CNPJ já cadastrado na base de dados em outra empresa. ");
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
			throw new DataIntegrityViolationException("A empresa possui um ou mais funcionários vinculados a ela. ");
		}
		empresaRepository.delete(empresa);
	}

	public Empresa consultarSaldo(final Long numSequencial) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(numSequencial);
		if (empresaOpt.isPresent()) {
			return empresaOpt.get();
		}
		throw new ObjectNotFoundException("Objeto não encontrado. ");
	}

	public void transferir(final TransacaoRequest request) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(request.getEmpresaNumSequencial());
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.getByCpf(request.getCpfFuncionario());

		if (!empresaOpt.isPresent()) {
			throw new ObjectNotFoundException("Empresa não encontrada. ");
		}

		if (!funcionarioOpt.isPresent()) {
			throw new ObjectNotFoundException("Funcionario não encontrado. ");
		}

		Empresa empresa = empresaOpt.get();
		Funcionario funcionario = funcionarioOpt.get();

		if (request.getValorTransacao().compareTo(empresa.getSaldoAtual()) == 1) {
			throw new DataIntegrityViolationException("Saldo insuficiente. ");
		}

		transacaoService.sacar(empresa.getNumSequencial(), request.getValorTransacao());
		transacaoService.depositar(funcionario.getNumSequencial(), request.getValorTransacao());

		empresaRepository.save(empresa);
		funcionarioRepository.save(funcionario);

	}

	public ResponseEntity<?> transferirC(final TransacaoRequest form) {
		Optional<Empresa> empresaOpt = empresaRepository.findById(form.getEmpresaNumSequencial());
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.getByCpf(form.getCpfFuncionario());

		if (!empresaOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Empresa não encontrada na base de dados. ");
		}

		if (!funcionarioOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Funcionário não encontrado na base de dados. ");
		}

		Empresa empresa = empresaOpt.get();
		Funcionario funcionario = funcionarioOpt.get();

		if (form.getValorTransacao().compareTo(empresa.getSaldoAtual()) == 1) {
			return ResponseEntity.badRequest().body("O saldo é insuficiente para realizar a transação. ");
		}

		empresa.sacar(form.getValorTransacao());
		funcionario.depositar(form.getValorTransacao());

		empresaRepository.save(empresa);
		funcionarioRepository.save(funcionario);

		return ResponseEntity.ok("Transação realizada com sucesso. ");
	}

	private Empresa findByCnpj(final EmpresaDTO empresaDto) {
		Empresa empresa = empresaRepository.findByCnpj(empresaDto.getCnpj());
		if (empresa != null) {
			return empresa;
		}
		return null;
	}
}
