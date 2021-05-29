package com.renato.projetoSomapay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.dto.FuncionarioDTO;
import com.renato.projetoSomapay.form.FuncionarioForm;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;

import net.minidev.json.JSONObject;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public ResponseEntity<?> consultarSaldo(Long numSequencial) {
		Optional<Funcionario> funcionarioOpt = repository.findById(numSequencial);
		if (funcionarioOpt.isPresent()) {
			JSONObject response = new JSONObject();
			response.appendField("saldo", funcionarioOpt.get().getSaldoAtual());
			
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> inserir(FuncionarioForm form) {
		Optional<Empresa> empresa = empresaRepository.findById(form.getEmpresaNumSequencial());
		
		if (!empresa.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível encontrar a empresa. ");
		}
		
		Funcionario funcionario = form.converter(empresa.get());
		
		boolean exists = repository.existsByCpf(funcionario.getCpf());
		
		if(exists) {
			return ResponseEntity.badRequest().body("Funcionário já cadastrado. ");
		}
		
		repository.save(funcionario);
		
		FuncionarioDTO funcionarioDto = new FuncionarioDTO();
		funcionarioDto.converter(funcionario);
		return ResponseEntity.created(null).body(funcionarioDto);
		
	}
}
