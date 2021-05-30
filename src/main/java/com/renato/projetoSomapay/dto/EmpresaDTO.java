package com.renato.projetoSomapay.dto;

import java.math.BigDecimal;

import com.renato.projetoSomapay.model.Empresa;

public class EmpresaDTO {

	private Long numSequencial;
	private String nome;
	private String cnpj;
	private String endereco;
	private BigDecimal saldoAtual;
	
	public EmpresaDTO() {
		super();
	}
	
	public EmpresaDTO(Empresa empresa) {
		super();
		this.numSequencial = empresa.getNumSequencial();
		this.nome = empresa.getNome();
		this.cnpj = empresa.getCnpj();
		this.endereco = empresa.getEndereco();
		this.saldoAtual = empresa.getSaldoAtual();
	}

	public void converter(final Empresa empresa) {
		this.numSequencial = empresa.getNumSequencial();
		this.nome = empresa.getNome();
		this.cnpj = empresa.getCnpj();
		this.endereco = empresa.getEndereco();
		this.saldoAtual = empresa.getSaldoAtual();
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

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
}
