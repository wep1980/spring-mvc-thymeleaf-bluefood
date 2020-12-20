package br.com.waldirep.bluefood.application.service;

import java.util.Iterator;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteComparator;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.SearchFilter;
import br.com.waldirep.bluefood.domain.restaurante.SearchFilter.SearchType;
import br.com.waldirep.bluefood.util.SecurityUtils;

@Service
public class RestauranteService {
	
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	/**
	 * REGRAS DE NEG�CIO -> Metodo que salva ou edita
	 * @param restaurante
	 * @throws ValidationException
	 */
 	@Transactional // executado em contexto de transa��o no banco de dados
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		if(restaurante.getId() != null) { // Se for edi��o
			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow(); // Pegando a senha do restaurante no BD
			restaurante.setSenha(restauranteDB.getSenha()); // Colocando a senha novamente no restaurante, pois ela n�o sofre altera��o
			restaurante.setLogotipo(restauranteDB.getLogotipo()); // Colocando a logo novamente no restaurante, pois ela n�o sofre altera��o
			restauranteRepository.save(restaurante);
			
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
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente != null) {
			return false;
		}
		
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
	
	
	
	/**
	 * Metodo que retorna a lista de restaurantes pesquisados
	 * Pesquisa feita por texto no campo de busca ou pelas categorias
	 * @param filter
	 * @return
	 */
	public List<Restaurante> search(SearchFilter filter){
		
		List<Restaurante> restaurantes;
		
		if(filter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
			
		} else if (filter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
			
		} else {
			throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + " não é suportado");
		}
		
		// Itarenado nos restaurantes que foram encontrados, com o iterator podemos modificar um item da cole��o, no caso remover.
		Iterator<Restaurante> it = restaurantes.iterator();
		while(it.hasNext()) {
			Restaurante restaurante = it.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue(); // doubleValue() -> convertendo para double
			
			// De acordo com a pesquisa retira ou n�o retira os restaurantes com entrega gratis
			if(filter.isEntregaGratis() && taxaEntrega > 0 || !filter.isEntregaGratis() && taxaEntrega == 0) {
				it.remove(); // remove os elementos da lista
			}
		}
		
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator); // Ordena a lista de restaurantes atraves do comparator
		
		return restaurantes;
	}
	
	
	
	@Transactional
	public void saveItemCardapio(ItemCardapio itemCardapio) {
		
		itemCardapio = itemCardapioRepository.save(itemCardapio);
		
		itemCardapio.setImagemFileName();
		imageService.uploadComida(itemCardapio.getImagemFile(), itemCardapio.getImagem());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
