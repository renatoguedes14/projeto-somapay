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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@NoArgsConstructor
@Getter
@Setter
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

	public Funcionario(String nome, String cpf, String endereco, BigDecimal saldoAtual, Empresa empresa) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.saldoAtual = saldoAtual;
		this.empresa = empresa;
	}
}
