package com.jlcb.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso buscarPorId(Long id) {
		return acessoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(String.format("Não existe cadastro de acesso com id %d", id)));
	}
	
	public List<Acesso> buscarPorDescricao(String descricao) {
		return acessoRepository.buscarPorDescricao(descricao);
	}
	
	public Acesso salvar(Acesso acesso) {
		return acessoRepository.save(acesso);
	}
	
	public void deletar(Long id) {
		acessoRepository.deleteById(id);
	}

}
