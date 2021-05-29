package com.renato.projetoSomapay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.dto.EmpresaDTO;
import com.renato.projetoSomapay.form.EmpresaForm;
import com.renato.projetoSomapay.form.TransacaoForm;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;

import net.minidev.json.JSONObject;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public ResponseEntity<?> consultarSalto(Long numSequencial) {
		Optional<Empresa> empresaOpt = repository.findById(numSequencial);
		if (empresaOpt.isPresent()) {
			JSONObject response = new JSONObject();
			response.appendField("saldo", empresaOpt.get().getSaldoAtual());

			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> inserir(EmpresaForm form) {
		Empresa empresa = form.converter();

		boolean exists = repository.existsByCnpj(empresa.getCnpj());

		if (exists) {
			return ResponseEntity.badRequest().body("Empresa já cadastrada na base de dados. ");
		}

		repository.save(empresa);

		EmpresaDTO empresaDto = new EmpresaDTO();
		empresaDto.converter(empresa);
		return ResponseEntity.created(null).body(empresaDto);
	}
	
	public ResponseEntity<?> transferir(TransacaoForm form) {
		Optional<Empresa> empresaOpt = repository.findById(form.getEmpresaNumSequencial());
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByCpf(form.getCpfFuncionario());
		
		if (!empresaOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Empresa não encontrada na base de dados. ");
		}
		
		if (!funcionarioOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Funcionário não encontrado na base de dados. ");
		}
		
		Empresa empresa = empresaOpt.get();
		Funcionario funcionario = funcionarioOpt.get();
		
		if(form.getValorTransacao().compareTo(empresa.getSaldoAtual()) == 1) {
			return ResponseEntity.badRequest().body("O saldo é insuficiente para realizar a transação. ");
		}
		
		empresa.sacar(form.getValorTransacao());
		funcionario.depositar(form.getValorTransacao());
		
		repository.save(empresa);
		funcionarioRepository.save(funcionario);
		
		return ResponseEntity.ok("Transação realizada com sucesso. ");
	}
}
