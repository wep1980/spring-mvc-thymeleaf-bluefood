package br.com.waldirep.bluefood.infrastructure.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.waldirep.bluefood.util.SecurityUtils;

/**
 * Classe que direciona o usuario dependendo se a autentica��o foi feita ou n�o
 * @author wepbi
 *
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	/**
	 * Metodo para fazer o direcionamento final para encerrar o processo de autentica��o
	 * Quando esse metodo e chamado, a autentica��o ja foi feita com sucesso
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Role role = SecurityUtils.loggedUser().getRole(); // Pegando o perfil de acesso do usuario logado
		if(role == Role.CLIENTE) {
			response.sendRedirect("cliente/home"); // Avisa ao navegador onde foi feita a requisi��o que tem que ser feito um direcionamento para esssa pagina
		}else if (role == Role.RESTAURANTE) {
			response.sendRedirect("restaurante/home");
		} else {
			throw new IllegalStateException("Erro na autenticação");
		}
	}

}
