package com.renato.projetoSomapay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long numSequencial;
	private BigDecimal valor;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtHrTransacao;
	@ManyToOne
	@JoinColumn(name = "empresa", referencedColumnName = "numSequencial")
	private Empresa empresa;
	@JoinColumn(name = "funcionario", referencedColumnName = "numSequencial")
	private Funcionario funcionario;

	public Transacao() {
		super();
	}

	public Long getNumSequencial() {
		return numSequencial;
	}

	public void setNumSequencial(Long numSequencial) {
		this.numSequencial = numSequencial;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDtHrTransacao() {
		return dtHrTransacao;
	}

	public void setDtHrTransacao(Date dtHrTransacao) {
		this.dtHrTransacao = dtHrTransacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public boolean calcularTransacao(Funcionario funcionario, Empresa empresa, BigDecimal valor) {
		BigDecimal saldoAtualEmpresa = empresa.getSaldoAtual();
		Date dtTransacao = new Date();
		if (saldoAtualEmpresa.compareTo(valor) >= 0 && valor.doubleValue() > 0) {
			empresa.setSaldoAtual(empresa.getSaldoAtual().subtract(valor));
			funcionario.setSaldoAtual(funcionario.getSaldoAtual().add(valor));
			this.setFuncionario(funcionario);
			this.setEmpresa(empresa);
			this.setValor(valor);
			this.setDtHrTransacao(dtTransacao);
			return true;
		}
		return false;
	}
	
}
