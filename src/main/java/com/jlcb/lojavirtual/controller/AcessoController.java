package com.jlcb.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.lojavirtual.model.Acesso;
import com.jlcb.lojavirtual.service.AcessoService;

@RestController
@RequestMapping("/acessos")
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) {
		Acesso acessoSalvo = acessoService.salvar(acesso);		
		return ResponseEntity.ok(acessoSalvo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		acessoService.deletar(id);
	}
	
}
