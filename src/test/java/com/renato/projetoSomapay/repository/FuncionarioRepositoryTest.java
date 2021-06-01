package com.renato.projetoSomapay.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.renato.projetoSomapay.model.Empresa;
import com.renato.projetoSomapay.model.Funcionario;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(OrderAnnotation.class)
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired 
	private EmpresaRepository empresaRepository;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testeInserirFuncionario() {
		Empresa empresa = empresaRepository.save(new Empresa("nome da empresa", "17.073.860/0001-58", "Rua Amadeu Alves, 31", BigDecimal.valueOf(500)));
		
		Funcionario funcionario = new Funcionario("nome comum", "909.129.940-10", "Rua Comum", BigDecimal.valueOf(50), empresa);
		Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
		
		assertNotNull(funcionarioSalvo);
	}
	
	@Test
	@Order(2)
	public void testeBuscarPorCpf_Encontrado() {
		String cpf = "909.129.940-10";
		Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
		
		assertThat(funcionario.getCpf()).isEqualTo(cpf);
	}
	
	@Test
	@Order(3)
	public void testeBuscarPorCpf_NaoEncontrado() {
		String cpf ="909.129.940-1";
		Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
		
		assertNull(funcionario);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testeAtualizarDados() {
		String oldCpf = "909.129.940-10";
		String newCpf = "601.588.210-76";
		Funcionario funcionario = funcionarioRepository.findByCpf(oldCpf);
		funcionario.setCpf(newCpf);
		
		funcionarioRepository.save(funcionario);
		
		Funcionario funcionarioAtualizado = funcionarioRepository.findByCpf(newCpf);
		
		assertThat(funcionarioAtualizado.getCpf()).isEqualTo(newCpf);
	}
	
	@Test
	@Order(5)
	public void testarListarFuncionarios() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		assertThat(funcionarios.size()).isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testarRemoverFuncionario() {
		Empresa empresa = empresaRepository.findByCnpj("17.073.860/0001-58");
		Funcionario funcionario = new Funcionario("Fausto Silva", "101.364.670-32", "Rua das Mansões", BigDecimal.valueOf(50000), empresa);
		funcionarioRepository.save(funcionario);
		
		boolean existeAntesDelete = funcionarioRepository.findById(funcionario.getNumSequencial()).isPresent();
		
		funcionarioRepository.deleteById(funcionario.getNumSequencial());
		
		boolean existeAposDelete = funcionarioRepository.findById(funcionario.getNumSequencial()).isPresent();
		
		assertTrue(existeAntesDelete);
		assertFalse(existeAposDelete);
	}
}
