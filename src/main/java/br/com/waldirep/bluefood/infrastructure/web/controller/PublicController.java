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

import br.com.waldirep.bluefood.application.service.ClienteService;
import br.com.waldirep.bluefood.application.service.RestauranteService;
import br.com.waldirep.bluefood.application.service.ValidationException;
import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;

// Classe que atende a demanda de URLs que s�o publicas (Spring security) -- Na mesma tela de cria��o sera feita a edi��o que e controlada por uma flag(ativado ou desativado) = boolean(true ou false)
@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	
	
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
		ControllerHelper.setEditMode(model, false); // false indica que sera um novo cliente -- True indica que sera edi��o de um cliente
		return "cliente-cadastro";
		
	}
	
	
	@GetMapping("/restaurante/new")
	public String newRestaurante(Model model) {
		/* Teste
		Cliente c = new Cliente();
		c.setNome("waldir");
		model.addAttribute("cliente", c);*/
	
		model.addAttribute("restaurante", new Restaurante()); // Adiciona o objeto
		ControllerHelper.setEditMode(model, false); // false indica que sera um novo cliente -- True indica que sera edi��o de um cliente
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model); // Carregando a lista de categorias
		return "restaurante-cadastro";
		
	}
	
	
	
	// cliente -> nome colocado no model -- @Valid -> Faz a valida��o entre o spring MVC e a BeanValidation APi
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, 
			                  Errors errors,
			                  Model model) {
		if(!errors.hasErrors()) { // Se n�o tiver erros salva o cliente
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!"); // Envia a mensagem de sucesso atrav�s de "msg"
			} catch (ValidationException e) {
				// rejectValue() -> rejeita a valida��o de um campo, no caso email, encaixa o erro com toda estrutura de erros criada com Model
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	
	
	@PostMapping(path = "/restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante, 
			                  Errors errors,
			                  Model model) {
		if(!errors.hasErrors()) { // Se n�o tiver erros salva o cliente
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso!"); // Envia a mensagem de sucesso atrav�s de "msg"
			} catch (ValidationException e) {
				// rejectValue() -> rejeita a valida��o de um campo, no caso email, encaixa o erro com toda estrutura de erros criada com Model
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
