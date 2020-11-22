package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.waldirep.bluefood.domain.cliente.Cliente;

// Classe que atende a demanda de URLs que são publicas (Spring security)
@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	
	/**
	 * Model e umas das formas que o spring tem de se comunicar com o HTML que esta sendo criado
	 * ARQUITETURA MVC
	 * @param model
	 * @return
	 */
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		
		/* Teste
		Cliente c = new Cliente();
		c.setNome("waldir");
		model.addAttribute("cliente", c);*/
	
		model.addAttribute("cliente", new Cliente()); // Adiciona o objeto e retorna para a view
		return "cliente-cadastro";
		
	}

}
