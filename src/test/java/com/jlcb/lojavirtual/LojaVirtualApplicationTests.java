package com.jlcb.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jlcb.lojavirtual.controller.AcessoController;
import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.repository.AcessoRepository;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {
	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testSalvarAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");
		assertEquals(true, acesso.getId() == null);
		
		acesso = acessoController.salvar(acesso).getBody();
		assertEquals(true, acesso.getId() > 0);
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
		
		Acesso acessoCarregado = acessoRepository.findById(acesso.getId()).get();
		assertEquals(acesso.getId(), acessoCarregado.getId());
		
		acessoController.deletar(acessoCarregado.getId());
		acessoRepository.flush();
		
		Acesso acessoDeletado = acessoRepository.findById(acessoCarregado.getId()).orElse(null);
		assertEquals(true, acessoDeletado == null);
		
		acesso = new Acesso();
		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoController.salvar(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoPelaDescricao("ALUNO".trim().toUpperCase());
		assertEquals(1, acessos.size());
		
		acessoController.deletar(acesso.getId());
	}

}
