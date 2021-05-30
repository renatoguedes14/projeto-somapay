package com.renato.projetoSomapay.dto;

import java.math.BigDecimal;

import com.renato.projetoSomapay.model.Funcionario;

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

	public Long getNumSequencial() {
		return numSequencial;
	}

	public void setNumSequencial(Long numSequencial) {
		this.numSequencial = numSequencial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getEmpresaNumSequencial() {
		return empresaNumSequencial;
	}

	public void setEmpresaNumSequencial(Long empresaNumSequencial) {
		this.empresaNumSequencial = empresaNumSequencial;
	}

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
}
