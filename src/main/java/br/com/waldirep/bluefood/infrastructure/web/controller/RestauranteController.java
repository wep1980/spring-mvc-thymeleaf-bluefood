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

import br.com.waldirep.bluefood.application.service.RestauranteService;
import br.com.waldirep.bluefood.application.service.ValidationException;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;
import br.com.waldirep.bluefood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {


	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	
	@Autowired
	private RestauranteService restauranteService;
	
	
	
	
	@GetMapping(path = "/home")
	public String home() {
		return "restaurante-home";
	}
	

	
	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId(); // pegando o Id do restaurante logado
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(); // .orElseThrow() -> Lan�a a excess�o caso o cliente n�o exista
																				 
		model.addAttribute("restaurante", restaurante);
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		
		return "restaurante-cadastro";
	}
	
	
	
	@PostMapping("/save")
	public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante, 
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) { // Se n�o tiver erros salva o cliente
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso!"); // Envia a mensagem de sucesso atrav�s de
																			// "msg"
			} catch (ValidationException e) {
                // rejectValue() -> rejeita a valida��o de um campo, no caso email, encaixa o erro com toda estrutura de erros criada com Model
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
