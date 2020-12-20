package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	/**
	 * Metodo que retorna a tela de login - MVC
	 * @return
	 */
	@GetMapping(path = {"/login", "/"})
	public String login() {
		return "login";
	}
	
	/**
	 * Mostra a tela de erro e redireciona para a tela de login
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/login-error")
	public String loginError(Model model) {
		model.addAttribute("msg", "Credenciais inválidas");
		return "login";
	}

}
