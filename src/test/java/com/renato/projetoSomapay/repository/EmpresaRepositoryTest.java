package com.renato.projetoSomapay.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(OrderAnnotation.class)
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testeInserirEmpresa() {
		Empresa empresa = new Empresa("nome da empresa", "17.073.860/0001-58", "Rua Amadeu Alves, 31", BigDecimal.valueOf(500));
		Empresa empresaSalva = empresaRepository.save(empresa);
		
		assertNotNull(empresaSalva);
	}
	
	@Test
	@Order(2)
	public void testeBuscarPorCnpj_Encontrado() {
		String cnpj = "17.073.860/0001-58";
		Empresa empresa = empresaRepository.findByCnpj(cnpj);
		
		assertThat(empresa.getCnpj()).isEqualTo(cnpj);
	}
	
	@Test
	@Order(3)
	public void testeBuscarPorCnpj_NaoEncontrado() {
		String cnpj = "17.073.860/0001-5";
		Empresa empresa = empresaRepository.findByCnpj(cnpj);
		
		assertNull(empresa);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testeAtualizarDados() {
		String oldCnpj = "17.073.860/0001-58";
		String newCnpj = "94.880.744/0001-91";
		Empresa empresa = empresaRepository.findByCnpj(oldCnpj);
		empresa.setCnpj(newCnpj);
		
		empresaRepository.save(empresa);
		
		Empresa empresaAtualizada = empresaRepository.findByCnpj(newCnpj);
		
		assertThat(empresaAtualizada.getCnpj()).isEqualTo(newCnpj); 
	}
	
	@Test
	@Order(5)
	public void testarListarEmpresas() {
		List<Empresa> empresas = empresaRepository.findAll();
		
		assertThat(empresas.size()).isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testarRemoverEmpresa() {
		Empresa empresa = new Empresa("Oito Informática", "33.710.430/0001-56", "Rua Egídio de Oliveira", BigDecimal.valueOf(78));
		empresaRepository.save(empresa);
		
		boolean existeAntesDelete = empresaRepository.findById(empresa.getNumSequencial()).isPresent();
		
		empresaRepository.deleteById(empresa.getNumSequencial());
		
		boolean existeAposDelete = empresaRepository.findById(empresa.getNumSequencial()).isPresent();
		
		assertTrue(existeAntesDelete);
		assertFalse(existeAposDelete);
	}
}
