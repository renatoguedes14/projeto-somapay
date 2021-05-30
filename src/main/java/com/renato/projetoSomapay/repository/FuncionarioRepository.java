package com.renato.projetoSomapay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.renato.projetoSomapay.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	boolean existsByCpf(String cpf);

	@Query("SELECT obj FROM Funcionario obj WHERE obj.cpf =:cpf")
	Funcionario findByCpf(@Param("cpf") String cpf);

	Optional<Funcionario> getByCpf(String cpf);
}
