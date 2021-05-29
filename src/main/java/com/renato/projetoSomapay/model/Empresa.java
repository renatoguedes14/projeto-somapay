package com.renato.projetoSomapay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long numSequencial;
	private String nome;
	private String cnpj;
	private String endereco;
	private BigDecimal saldoAtual;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
	private List<Funcionario> funcionariosList;

	public Empresa() {
		super();
	}

	public Empresa(String nome, String cnpj, String endereco, BigDecimal saldoAtual) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.saldoAtual = saldoAtual;
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

	public List<Funcionario> getFuncionariosList() {
		return funcionariosList;
	}

	public void setFuncionariosList(List<Funcionario> funcionariosList) {
		this.funcionariosList = funcionariosList;
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
