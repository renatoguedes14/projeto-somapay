package com.renato.projetoSomapay.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "num_sequencial")
	private Long numSequencial;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "saldo_atual")
	private BigDecimal saldoAtual;
	@ManyToOne
	@JoinColumn(name = "empresa", referencedColumnName = "num_sequencial")
	private Empresa empresa;

	public Funcionario() {
		super();
	}

	public Funcionario(String nome, String cpf, String endereco, BigDecimal saldoAtual, Empresa empresa) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.saldoAtual = saldoAtual;
		this.empresa = empresa;
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

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void sacar(BigDecimal valor) {
		if (valor.compareTo(BigDecimal.ZERO) == 1 && valor.compareTo(this.saldoAtual) <= 0) {
			this.saldoAtual = this.saldoAtual.subtract(valor);
		}
	}

	public void depositar(BigDecimal valor) {
		if (valor.compareTo(BigDecimal.ZERO) == 1) {
			this.saldoAtual = this.saldoAtual.add(valor);
		}
	}
}
