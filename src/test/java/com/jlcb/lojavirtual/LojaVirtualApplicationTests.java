package com.jlcb.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlcb.lojavirtual.controller.AcessoController;
import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.repository.AcessoRepository;

import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {
	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Test
	public void testRestApiBuscarAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_BUSCAR_POR_ID");
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
				                   MockMvcRequestBuilders.get("/acessos/" + acesso.getId())
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		Acesso acessoRetorno = objectMapper
	               .readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
	
		assertEquals(acesso, acessoRetorno);
	}
	
	@Test
	public void testRestApiBuscarAcessoPorDescricao() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_BUSCAR_POR_DESCRICAO");
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
				                   MockMvcRequestBuilders.get("/acessos/por-descricao/" + "POR_DESCRICAO")
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

		List<Acesso> acessosRetornoList = objectMapper
	               .readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});
		assertEquals(1, acessosRetornoList.size());
		assertEquals(acesso, acessosRetornoList.get(0));
		
		acessoController.deletar(acesso.getId());
	}
	
	@Test
	public void testRestApiCadastrarAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
				                   MockMvcRequestBuilders.post("/acessos")
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		/* Converter o retorno da API para um objeto de acesso */
		Acesso acessoRetorno = objectMapper
				               .readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
	}
	
	@Test
	public void testRestApiDeletarAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(
				                   MockMvcRequestBuilders.delete("/acessos/" + acesso.getId())
				                   .content(objectMapper.writeValueAsString(acesso))
				                   .accept(MediaType.APPLICATION_JSON)
				                   .contentType(MediaType.APPLICATION_JSON));
		assertEquals(204, retornoApi.andReturn().getResponse().getStatus());
	}

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
		Acesso acessoDeletado = acessoRepository.findById(acessoCarregado.getId()).orElse(null);
		assertEquals(true, acessoDeletado == null);
		
		acesso = new Acesso();
		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoController.salvar(acesso).getBody();
		List<Acesso> acessos = acessoRepository.buscarPorDescricao("ALUNO".trim().toUpperCase());
		assertEquals(1, acessos.size());
		acessoController.deletar(acesso.getId());
	}

}
