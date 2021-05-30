package com.renato.projetoSomapay.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renato.projetoSomapay.controller.request.TransacaoRequest;
import com.renato.projetoSomapay.dto.EmpresaDTO;
import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@GetMapping("/{numSequencial}")
	public ResponseEntity<EmpresaDTO> buscar(@PathVariable Long numSequencial) {
		EmpresaDTO empresaDto = new EmpresaDTO(empresaService.buscarEmpresa(numSequencial));
		return ResponseEntity.ok().body(empresaDto);
	}

	@GetMapping
	public ResponseEntity<List<EmpresaDTO>> buscarTodos() {
		List<EmpresaDTO> empresaDtoList = empresaService.buscarTodos().stream().map(obj -> new EmpresaDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(empresaDtoList);
	}

	@PostMapping
	public ResponseEntity<EmpresaDTO> inserir(@Valid @RequestBody EmpresaDTO empresaDto) {
		Empresa empresa = empresaService.inserir(empresaDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSequencial}")
				.buildAndExpand(empresa.getNumSequencial()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{numSequencial}")
	public ResponseEntity<EmpresaDTO> atualizarDados(@PathVariable Long numSequencial,
			@Valid @RequestBody EmpresaDTO empresaDto) {
		EmpresaDTO novaEmpresa = new EmpresaDTO(empresaService.atualizarDados(numSequencial, empresaDto));
		return ResponseEntity.ok().body(novaEmpresa);
	}

	@DeleteMapping("/{numSequencial}")
	public ResponseEntity<Void> remover(@PathVariable Long numSequencial) {
		empresaService.remover(numSequencial);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable final Long numSequencial) {
		EmpresaDTO empresaDto = new EmpresaDTO(empresaService.consultarSaldo(numSequencial));
		return ResponseEntity.ok().body(empresaDto.getSaldoAtual());
	}

	@PostMapping("/transferir")
	public ResponseEntity<?> transferir(@Valid @RequestBody TransacaoRequest request) {
		empresaService.transferir(request);
		return ResponseEntity.ok("Transação realizada com sucesso. ");
	}
}
