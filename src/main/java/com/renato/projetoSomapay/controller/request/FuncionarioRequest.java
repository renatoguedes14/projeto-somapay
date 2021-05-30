package com.renato.projetoSomapay.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;

public class FuncionarioRequest {

	@NotNull
	private String nome;
	@NotNull
	private String cpf;
	@NotNull
	private String endereco;
	@NotNull
	private BigDecimal saldo;
	@NotNull
	private Long empresaNumSequencial;

	public Funcionario converter(Empresa empresa) {
		Funcionario funcionario = new Funcionario(this.nome, this.cpf, this.endereco, this.saldo, empresa);
		return funcionario;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getEmpresaNumSequencial() {
		return empresaNumSequencial;
	}

	public void setEmpresaNumSequencial(Long empresaNumSequencial) {
		this.empresaNumSequencial = empresaNumSequencial;
	}
}
