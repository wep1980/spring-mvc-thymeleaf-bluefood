package br.com.waldirep.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.waldirep.bluefood.application.ClienteService;
import br.com.waldirep.bluefood.application.ValidationException;
import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.waldirep.bluefood.util.SecurityUtils;

/**
 * Classe responsavel pelas operações de um cliente logado
 * 
 * @author wepbi
 *
 */
@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	

	/**
	 * Encontra todas as categorias
	 * Recebe um model como parametro
	 * @return
	 */
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome")); // Busca todas as categorias ordenadas por nome
		model.addAttribute("categorias", categorias);
		
		return "cliente-home";
	}
	
	

	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer clienteId = SecurityUtils.loggedCliente().getId(); // pegando o Id do cliente logado
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(); // .orElseThrow() -> Lança a excessão
																				// caso o cliente não exista
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	

	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) { // Se não tiver erros salva o cliente
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!"); // Envia a mensagem de sucesso através de
																			// "msg"
			} catch (ValidationException e) {
                // rejectValue() -> rejeita a validação de um campo, no caso email, encaixa o erro com toda estrutura de erros criada com Model
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";

	}

}
