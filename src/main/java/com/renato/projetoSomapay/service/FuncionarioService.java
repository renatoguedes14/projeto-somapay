package com.renato.projetoSomapay.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projetoSomapay.controller.request.FuncionarioRequest;
import com.renato.projetoSomapay.dto.FuncionarioDTO;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.repository.EmpresaRepository;
import com.renato.projetoSomapay.repository.FuncionarioRepository;

import net.minidev.json.JSONObject;

@Service
@Transactional
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public ResponseEntity<?> consultarSaldo(Long numSequencial) {
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(numSequencial);
		if (funcionarioOpt.isPresent()) {
			JSONObject response = new JSONObject();
			response.appendField("saldo", funcionarioOpt.get().getSaldoAtual());
			
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> inserir(FuncionarioRequest form) {
		Optional<Empresa> empresa = empresaRepository.findById(form.getEmpresaNumSequencial());
		
		if (!empresa.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível encontrar a empresa. ");
		}
		
		Funcionario funcionario = form.converter(empresa.get());
		
		boolean exists = funcionarioRepository.existsByCpf(funcionario.getCpf());
		
		if(exists) {
			return ResponseEntity.badRequest().body("Funcionário já cadastrado. ");
		}
		
		funcionarioRepository.save(funcionario);
		
		FuncionarioDTO funcionarioDto = new FuncionarioDTO();
		funcionarioDto.converter(funcionario);
		return ResponseEntity.created(null).body(funcionarioDto);
		
	}
}
