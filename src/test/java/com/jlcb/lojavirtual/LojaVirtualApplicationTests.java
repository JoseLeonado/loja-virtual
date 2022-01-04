package com.jlcb.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jlcb.lojavirtual.controller.AcessoController;
import com.jlcb.lojavirtual.model.Acesso;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests {
	
	@Autowired
	private AcessoController acessoController;

	@Test
	public void testSalvarAcesso() {
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoController.salvar(acesso);
	}

}
