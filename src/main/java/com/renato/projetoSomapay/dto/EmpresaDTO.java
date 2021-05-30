package com.renato.projetoSomapay.dto;

import java.math.BigDecimal;

import com.renato.projetoSomapay.model.Empresa;

import lombok.Data;

@Data
public class EmpresaDTO {

	private Long numSequencial;
	private String nome;
	private String cnpj;
	private String endereco;
	private BigDecimal saldoAtual;

	public EmpresaDTO() {
		super();
	}

	public EmpresaDTO(final Empresa empresa) {
		super();
		this.numSequencial = empresa.getNumSequencial();
		this.nome = empresa.getNome();
		this.cnpj = empresa.getCnpj();
		this.endereco = empresa.getEndereco();
		this.saldoAtual = empresa.getSaldoAtual();
	}
}
