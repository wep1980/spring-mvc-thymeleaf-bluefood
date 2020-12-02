package br.com.waldirep.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;

@Service
public class RestauranteService {
	
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ImageService imageService;
	
	/**
	 * REGRAS DE NEG�CIO -> Metodo que salva ou edita
	 * @param restaurante
	 * @throws ValidationException
	 */
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail est� duplicado");
		}
		
		if(restaurante.getId() != null) { // Se for edi��o
			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow(); // Pegando a senha do restaurante no BD
			restaurante.setSenha(restauranteDB.getSenha()); // Colocando a senha novamente no restaurante
			
		}else { // Se for um restaurante novo
			restaurante.encryptPassword(); // criptografa a senha
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName(); // Colocando o nome da imagem
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
		
		
	}
	
	
	/**
	 * M�todo para validar email, true = email valido, false = email duplicado
	 * 
	 * REGRA DE NEGOCIO -> Pesquisa no BD se existe algum cadastro com o mesmo email, se n�o existir o email pode cadastrar.
	 *                     Se o email ja existir, � verificado se a opera��o e de inserir ou editar.
	 *                     Se o email ja existe e � uma edi��o, n�o � permitido colocar um email ja cadastrado.
	 *                     Se o email ja existe e esta sendo feita uma altera��o de email do proprio registro e permitido a altera��o
	 *                     
	 * @param cliente
	 * @param id
	 * @return
	 */
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restauranteEmail = restauranteRepository.findByEmail(email);
		if(restauranteEmail != null) { // Se existir o email (Foi encontrado um cliente com o mesmo email) retorna false
			if(id == null) { // Se existe o email e o id e null, O cliente n�o existe ainda porem esta tentando cadastrar um email que ja existe - retorna null
				return false; // RETORNA FALSE PORQUE NO NOVO CADASTRO JA EXISTE UM EMAIL IGUAL
			}
			if(!restauranteEmail.getId().equals(id)) { // se existe o email e um cliente ja cadastrado esta tentando editar o email com um que ja existe retorna false 
				return false; 
			}
			
		}
		return true;
	}

}
