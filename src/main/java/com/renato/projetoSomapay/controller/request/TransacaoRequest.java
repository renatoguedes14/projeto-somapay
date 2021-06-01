package com.renato.projetoSomapay.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequest {

	@NotNull
	@Setter(AccessLevel.NONE)
	private Long empresaNumSequencial;
	@NotNull
	private String cpfFuncionario;
	@NotNull
	@DecimalMin(value = "0.0")
	private BigDecimal valorTransacao;

}
