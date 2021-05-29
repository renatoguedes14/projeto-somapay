package com.renato.projetoSomapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projetoSomapay.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	boolean existsByCnpj(String cnpj);
}
