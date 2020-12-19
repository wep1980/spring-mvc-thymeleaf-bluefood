package br.com.waldirep.bluefood.infrastructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration // avisa ao spring que e uma classe para configuração
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	/**
	 * Metodo que cria uma instancia de AuthenticationSuccessHandler
	 * @return
	 */
	@Bean // Produz instancia e é utilizada quando o spring necessita
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}
	
	/**
	 * Configura o processo de autenticação e autorização
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests() //Permite dizer o que e autorizado ou não, depois de autenticado quais URLs tem permissão
		    .antMatchers("/images/**", "/css/**", "/js/**", "/public/**", "/webservice").permitAll() // permite padrões de acesso, todos esses liberados
	        .antMatchers("/cliente/**").hasRole(Role.CLIENTE.toString()) // acesso para cliente
	        .antMatchers("/restaurante/**").hasRole(Role.RESTAURANTE.toString()) // acesso para restaurante
	        .anyRequest().authenticated() // qualquer outra requisição que nao esteja listada acima precisa estar autenticado
	        .and()
		        .formLogin() // Sera usado um formLogin para autenticação
		           .loginPage("/login") // pagina de login
		           .failureUrl("/login-error") // pagina de erro ao fazer login
		           .successHandler(authenticationSuccessHandler()) // Objeto chamado quando a autenticação funcionar
		           .permitAll()
		        .and()
		           .logout().logoutUrl("/logout") // pagina de logout
		           .permitAll();
	}

}
