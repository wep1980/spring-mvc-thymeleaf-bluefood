package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.waldirep.bluefood.application.ClienteService;
import br.com.waldirep.bluefood.domain.cliente.Cliente;

// Classe que atende a demanda de URLs que são publicas (Spring security) -- Na mesma tela de criação sera feita a edição que e controlada por uma flag(ativado ou desativado) = boolean(true ou false)
@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
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
	
		model.addAttribute("cliente", new Cliente()); // Adiciona o objeto
		ControllerHelper.setEditMode(model, true); // false indica que não sera uma edição
		return "cliente-cadastro";
		
	}
	
	// cliente -> nome colocado no model
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.saveCliente(cliente);
		return "cliente-cadastro";
	}

}
