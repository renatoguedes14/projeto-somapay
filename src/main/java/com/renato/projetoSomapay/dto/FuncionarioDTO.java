package com.renato.projetoSomapay.dto;

import java.math.BigDecimal;

import com.renato.projetoSomapay.model.Funcionario;

import lombok.Data;

@Data
public class FuncionarioDTO {

	private Long numSequencial;
	private String nome;
	private String cpf;
	private String endereco;
	private Long empresaNumSequencial;
	private BigDecimal saldoAtual;

	public FuncionarioDTO() {
		super();
	}

	public FuncionarioDTO(final Funcionario funcionario) {
		this.numSequencial = funcionario.getNumSequencial();
		this.nome = funcionario.getNome();
		this.cpf = funcionario.getCpf();
		this.endereco = funcionario.getEndereco();
		this.empresaNumSequencial = funcionario.getEmpresa().getNumSequencial();
		this.saldoAtual = funcionario.getSaldoAtual();
	}
}
