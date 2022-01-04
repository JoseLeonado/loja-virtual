package com.jlcb.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.service.AcessoService;

@Controller
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@PostMapping
	public Acesso salvar(Acesso acesso) {
		return acessoService.salvar(acesso);
	}
	
}
