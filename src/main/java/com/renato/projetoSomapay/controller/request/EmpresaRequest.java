package com.renato.projetoSomapay.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.renato.projetoSomapay.model.Empresa;

public class EmpresaRequest {

	@NotNull
	private String nome;
	@NotNull
	private String cnpj;
	@NotNull
	private String endereco;
	@NotNull
	private BigDecimal saldo;
	
	public Empresa converter() {
		Empresa empresa = new Empresa(this.nome, this.cnpj, this.endereco, this.saldo);
		return empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
}
