package br.com.waldirep.bluefood.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	
	/**
	 * REGRAS DE NEGÓCIO -> Metodo que salva ou edita
	 * @param cliente
	 * @throws ValidationException
	 */
	@Transactional // executado em contexto de transação no banco de dados
	public void saveCliente(Cliente cliente) throws ValidationException {
		
		if(!validateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		if(cliente.getId() != null) { // Se for edição
			Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow(); // Pegando a senha do cliente no BD
			cliente.setSenha(clienteDB.getSenha()); // Colocando a senha novamente no cliente
		}else { // Se for um cliente novo
			cliente.encryptPassword(); // criptografa a senha
		}
		clienteRepository.save(cliente);
	}
	
	
	
	/**
	 * Método para validar email, true = email valido, false = email duplicado
	 * 
	 * REGRA DE NEGOCIO -> Pesquisa no BD se existe algum cadastro com o mesmo email, se não existir o email pode cadastrar.
	 *                     Se o email ja existir, é verificado se a operação e de inserir ou editar.
	 *                     Se o email ja existe e é uma edição, não é permitido colocar um email ja cadastrado.
	 *                     Se o email ja existe e esta sendo feita uma alteração de email do proprio registro e permitido a alteração
	 *                     
	 * @param cliente
	 * @param id
	 * @return
	 */
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		if(restaurante != null) {
			return false;
		}
		
		Cliente clienteEmail = clienteRepository.findByEmail(email);
		if(clienteEmail != null) { // Se existir o email (Foi encontrado um cliente com o mesmo email) retorna false
			if(id == null) { // Se existe o email e o id e null, O cliente não existe ainda porem esta tentando cadastrar um email que ja existe - retorna null
				return false; // RETORNA FALSE PORQUE NO NOVO CADASTRO JA EXISTE UM EMAIL IGUAL
			}
			if(!clienteEmail.getId().equals(id)) { // se existe o email e um cliente ja cadastrado esta tentando editar o email com um que ja existe retorna false 
				return false; 
			}
			
		}
		return true;
	}
}
