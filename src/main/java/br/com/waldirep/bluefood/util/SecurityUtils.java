package br.com.waldirep.bluefood.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.infrastructure.web.security.LoggedUser;

/**
 * Classe com alguns ultilitarios de seguran�a
 * @author wepbi
 *
 */
public class SecurityUtils {

	
	/**
	 * Metodo que retorna o usuario logado
	 * @return
	 */
	public static LoggedUser loggedUser() {
		
		// Pegando o usuario logado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		/*
		 * Em alguma situa��es em que o authentication vai existir como uma autentica��o anonima
		 * (como se n�o tivesse autenticado)
		 */
		if(authentication instanceof AnonymousAuthenticationToken) {
			return null; // n�o logado
		}
		return (LoggedUser) authentication.getPrincipal();
	}
	
	
	
	// Metodo que pega o cliente logado
	public static Cliente loggedCliente() {
		LoggedUser loggedUser = loggedUser(); // pegando o usuario logado
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe um usuário logado");
		}
		if(!(loggedUser.getUsuario() instanceof Cliente)) { // Se n�o for cliente
			throw new IllegalStateException("O usuário logado não é um cliente");
		}
		return (Cliente) loggedUser.getUsuario(); // Retorna o cliente logado
	}
	
	
	
	
	// Metodo que pega o cliente logado
		public static Restaurante loggedRestaurante() {
			LoggedUser loggedUser = loggedUser(); // pegando o usuario logado
			if(loggedUser == null) {
				throw new IllegalStateException("Não existe um usuário logado");
			}
			if(!(loggedUser.getUsuario() instanceof Restaurante)) { // Se n�o for cliente
				throw new IllegalStateException("O usuário logado não á um restaurante");
			}
			return (Restaurante) loggedUser.getUsuario(); // Retorna o cliente logado
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
