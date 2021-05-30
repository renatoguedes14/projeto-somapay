package com.renato.projetoSomapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.renato.projetoSomapay.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	boolean existsByCnpj(String cnpj);

	@Query("SELECT obj FROM Empresa obj WHERE obj.cnpj =:cnpj")
	Empresa findByCnpj(@Param("cnpj") String cnpj);
}
