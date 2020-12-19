package br.com.waldirep.bluefood.infrastructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration // avisa ao spring que e uma classe para configura��o
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	/**
	 * Metodo que cria uma instancia de AuthenticationSuccessHandler
	 * @return
	 */
	@Bean // Produz instancia e � utilizada quando o spring necessita
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}
	
	/**
	 * Configura o processo de autentica��o e autoriza��o
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests() //Permite dizer o que e autorizado ou n�o, depois de autenticado quais URLs tem permiss�o
		    .antMatchers("/images/**", "/css/**", "/js/**", "/public/**", "/webservice").permitAll() // permite padr�es de acesso, todos esses liberados
	        .antMatchers("/cliente/**").hasRole(Role.CLIENTE.toString()) // acesso para cliente
	        .antMatchers("/restaurante/**").hasRole(Role.RESTAURANTE.toString()) // acesso para restaurante
	        .anyRequest().authenticated() // qualquer outra requisi��o que nao esteja listada acima precisa estar autenticado
	        .and()
		        .formLogin() // Sera usado um formLogin para autentica��o
		           .loginPage("/login") // pagina de login
		           .failureUrl("/login-error") // pagina de erro ao fazer login
		           .successHandler(authenticationSuccessHandler()) // Objeto chamado quando a autentica��o funcionar
		           .permitAll()
		        .and()
		           .logout().logoutUrl("/logout") // pagina de logout
		           .permitAll();
	}

}
