package com.renato.projetoSomapay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projetoSomapay.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	boolean existsByCpf(String cpf);
	
	Optional<Funcionario> findByCpf(String cpf);
}
