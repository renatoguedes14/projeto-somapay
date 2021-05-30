package com.renato.projetoSomapay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "num_sequencial")
	private Long numSequencial;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "saldo_atual")
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
}
