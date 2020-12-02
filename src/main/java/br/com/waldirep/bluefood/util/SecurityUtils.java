package br.com.waldirep.bluefood.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.infrastructure.web.security.LoggedUser;

/**
 * Classe com alguns ultilitarios de segurança
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
		 * Em alguma situações em que o authentication vai existir como uma autenticação anonima
		 * (como se não tivesse autenticado)
		 */
		if(authentication instanceof AnonymousAuthenticationToken) {
			return null; // não logado
		}
		return (LoggedUser) authentication.getPrincipal();
	}
	
	
	
	// Metodo que pega o cliente logado
	public static Cliente loggedCliente() {
		LoggedUser loggedUser = loggedUser(); // pegando o usuario logado
		if(loggedUser == null) {
			throw new IllegalStateException("Não existe um usuário logado");
		}
		if(!(loggedUser.getUsuario() instanceof Cliente)) { // Se não for cliente
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
			if(!(loggedUser.getUsuario() instanceof Restaurante)) { // Se não for cliente
				throw new IllegalStateException("O usuário logado não é um restaurante");
			}
			return (Restaurante) loggedUser.getUsuario(); // Retorna o cliente logado
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
