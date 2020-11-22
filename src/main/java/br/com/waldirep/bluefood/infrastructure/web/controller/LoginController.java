package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
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

}
