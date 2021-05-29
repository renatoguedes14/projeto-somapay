package com.renato.projetoSomapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projetoSomapay.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
