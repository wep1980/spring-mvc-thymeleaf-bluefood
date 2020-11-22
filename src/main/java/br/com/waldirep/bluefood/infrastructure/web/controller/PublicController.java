package br.com.waldirep.bluefood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
		ControllerHelper.setEditMode(model, false); // false indica que sera um novo cliente -- True indica que sera edição de um cliente
		return "cliente-cadastro";
		
	}
	
	
	
	// cliente -> nome colocado no model -- @Valid -> Faz a validação entre o spring MVC e a BeanValidation APi
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, 
			                  Errors errors,
			                  Model model) {
		if(!errors.hasErrors()) { // Se não tiver erros salva o cliente
			clienteService.saveCliente(cliente);
			model.addAttribute("msg", "Cliente gravado com sucesso!"); // Envia a mensagem de sucesso através de "msg"
		}
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}

}
