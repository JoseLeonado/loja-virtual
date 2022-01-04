package com.jlcb.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso salvar(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

}
