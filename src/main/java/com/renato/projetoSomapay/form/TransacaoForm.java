package com.renato.projetoSomapay.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransacaoForm {

	@NotNull
	private Long empresaNumSequencial;
	@NotNull
	private String cpfFuncionario;
	@NotNull
	@DecimalMin(value = "0.0")
	private BigDecimal valorTransacao;

	public Long getEmpresaNumSequencial() {
		return empresaNumSequencial;
	}

	public void setEmpresaNumSequencial(Long empresaNumSequencial) {
		this.empresaNumSequencial = empresaNumSequencial;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	public BigDecimal getValorTransacao() {
		return valorTransacao;
	}

	public void setValorTransacao(BigDecimal valorTransacao) {
		this.valorTransacao = valorTransacao;
	}
}
