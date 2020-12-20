package br.com.waldirep.bluefood.infrastructure.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.usuario.Usuario;

/**
 * Classe que representa um usuario logado
 * @author wepbi
 *
 */
@SuppressWarnings("serial")
public class LoggedUser implements UserDetails{
	
	
	private Usuario usuario;
	
	private Role role;
	
	private Collection<? extends GrantedAuthority> roles; 
	
	
	/**
	 * Metodo construtor
	 * @param usuario
	 */
	public LoggedUser(Usuario usuario) {
		this.usuario = usuario;
		
		Role role; // Descobrindo qual e o role do usuario
		if(usuario instanceof Cliente) {
			role = Role.CLIENTE;
		}else if (usuario instanceof Restaurante) {
			role = Role.RESTAURANTE;
		} else {
			throw new IllegalStateException("O tipo de usuário não é válido");
		}
		this.role = role;
		// Por padr�o quando ocorre o processo de autentica��o o spring coloca no inicio dos dos roles ROLE_
		this.roles = List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}
	
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // conta sempre ativa
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // conta sempre desbloqueada
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // conta com as credencias sempre validas
	}

	@Override
	public boolean isEnabled() {
		return true; // conta sempre habilitada
	}
	
	public Role getRole() {
		return role;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

}
