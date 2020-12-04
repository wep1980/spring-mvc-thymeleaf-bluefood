package br.com.waldirep.bluefood.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;
import br.com.waldirep.bluefood.domain.usuario.Usuario;

/**
 * Classe de serviço que carrega o usuario, para que o sprinf verifique se o usuario pode se autenticar ou não
 * Busca o usuario no banco de dados
 * @author wepbi
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	/**
	 * Metodo que carrega o usuario que vai ser autenticado
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = clienteRepository.findByEmail(username);
		
		if(usuario == null) {
			usuario = restauranteRepository.findByEmail(username);
			
			if(usuario == null) {
				throw new UsernameNotFoundException(username);
			}
		}
		
		return new LoggedUser(usuario);
	}

}
