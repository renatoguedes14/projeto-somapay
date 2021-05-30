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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/empresa", description = "Operações de cadastro de empresa, busca, atualização, remoção, consulta de saldo e transferência para funcionário. ")
@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@ApiOperation(value = "Realiza a busca de uma empresa pelo seu número sequencial único. ", response = EmpresaDTO.class)
	@GetMapping("/{numSequencial}")
	public ResponseEntity<EmpresaDTO> buscar(@PathVariable final Long numSequencial) {
		EmpresaDTO empresaDto = new EmpresaDTO(empresaService.buscarEmpresa(numSequencial));
		return ResponseEntity.ok().body(empresaDto);
	}

	@ApiOperation(value = "Realiza a busca de todas as empresas inseridas. ", response = EmpresaDTO.class, responseContainer = "List")
	@GetMapping
	public ResponseEntity<List<EmpresaDTO>> buscarTodos() {
		List<EmpresaDTO> empresaDtoList = empresaService.buscarTodos().stream().map(obj -> new EmpresaDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(empresaDtoList);
	}

	@ApiOperation(value = "Realiza a inserção de uma empresa com os dados inseridos no corpo da requisição. ")
	@PostMapping
	public ResponseEntity<EmpresaDTO> inserir(@Valid @RequestBody final EmpresaDTO empresaDto) {
		Empresa empresa = empresaService.inserir(empresaDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSequencial}")
				.buildAndExpand(empresa.getNumSequencial()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualiza os dados desejados de uma empresa já inserida. ")
	@PutMapping("/{numSequencial}")
	public ResponseEntity<EmpresaDTO> atualizarDados(@PathVariable final Long numSequencial,
			@Valid @RequestBody EmpresaDTO empresaDto) {
		EmpresaDTO novaEmpresa = new EmpresaDTO(empresaService.atualizarDados(numSequencial, empresaDto));
		return ResponseEntity.ok().body(novaEmpresa);
	}

	@ApiOperation(value = "Remove a empresa desejada a partir do seu número sequencial único. ")
	@DeleteMapping("/{numSequencial}")
	public ResponseEntity<Void> remover(@PathVariable final Long numSequencial) {
		empresaService.remover(numSequencial);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Realiza a consulta de saldo da empresa desejada. ")
	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable final Long numSequencial) {
		EmpresaDTO empresaDto = new EmpresaDTO(empresaService.consultarSaldo(numSequencial));
		return ResponseEntity.ok().body(empresaDto.getSaldoAtual());
	}

	@ApiOperation(value = "Realiza uma transação de qualquer valor para o funcionário desejado. ")
	@PostMapping("/transferir")
	public ResponseEntity<?> transferir(@Valid @RequestBody final TransacaoRequest request) {
		empresaService.transferir(request);
		return ResponseEntity.ok("Transação realizada com sucesso. ");
	}
}
