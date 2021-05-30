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

import com.renato.projetoSomapay.dto.FuncionarioDTO;
import com.renato.projetoSomapay.model.Funcionario;
import com.renato.projetoSomapay.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "/funcionario", description = "Operações de cadastro de funcionário, busca, atualização, remoção e consulta de saldo. ")
@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@ApiOperation(value = "Realiza a busca de um funcionário pelo seu número sequencial único. ")
	@GetMapping("/{numSequencial}")
	public ResponseEntity<FuncionarioDTO> buscar(@PathVariable final Long numSequencial) {
		FuncionarioDTO funcionarioDto = new FuncionarioDTO(funcionarioService.buscarFuncionario(numSequencial));
		return ResponseEntity.ok().body(funcionarioDto);
	}

	@ApiOperation(value = "Realiza a busca de todas os funcionários inseridos. ")
	@GetMapping
	public ResponseEntity<List<FuncionarioDTO>> buscarTodos() {
		List<FuncionarioDTO> funcionarioDtoList = funcionarioService.buscarTodos().stream()
				.map(obj -> new FuncionarioDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(funcionarioDtoList);
	}

	@ApiOperation(value = "Realiza a inserção de um funcionário com os dados inseridos no corpo da requisição. ")
	@PostMapping
	public ResponseEntity<FuncionarioDTO> inserir(@Valid @RequestBody final FuncionarioDTO funcionarioDto) {
		Funcionario funcionario = funcionarioService.inserir(funcionarioDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSequencial}")
				.buildAndExpand(funcionario.getNumSequencial()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualiza os dados desejados de um funcionario já inserido. ")
	@PutMapping("/{numSequencial}")
	public ResponseEntity<FuncionarioDTO> atualizarDados(@PathVariable final Long numSequencial,
			@Valid @RequestBody FuncionarioDTO funcionarioDto) {
		FuncionarioDTO novoFuncionario = new FuncionarioDTO(
				funcionarioService.atualizarDados(numSequencial, funcionarioDto));
		return ResponseEntity.ok().body(novoFuncionario);
	}

	@ApiOperation(value = "Remove o funcionario desejado a partir do seu número sequencial único. ")
	@DeleteMapping("/{numSequencial}")
	public ResponseEntity<Void> remover(@PathVariable final Long numSequencial) {
		funcionarioService.remover(numSequencial);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Realiza a consulta de saldo do funcionario desejado. ")
	@GetMapping("/saldo/{numSequencial}")
	public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable final Long numSequencial) {
		FuncionarioDTO funcionarioDto = new FuncionarioDTO(funcionarioService.consultarSaldo(numSequencial));
		return ResponseEntity.ok().body(funcionarioDto.getSaldoAtual());
	}
}
