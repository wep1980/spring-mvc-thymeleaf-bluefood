package br.com.waldirep.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.waldirep.bluefood.application.service.ClienteService;
import br.com.waldirep.bluefood.application.service.RestauranteService;
import br.com.waldirep.bluefood.application.service.ValidationException;
import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.SearchFilter;
import br.com.waldirep.bluefood.util.SecurityUtils;

/**
 * Classe responsavel pelas opera��es de um cliente logado
 * 
 * @author wepbi
 *
 */
@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	

	/**
	 * Encontra todas as categorias
	 * Recebe um model como parametro
	 * @return
	 */
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome")); // Busca todas as categorias ordenadas por nome
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter()); // Disponibiliza o searchFilter na tela home, o atributo texto sera colocado dentro do searchFilter
		
		List<Pedido> pedidos = pedidoRepository.listPedidosByCliente(SecurityUtils.loggedCliente().getId()); // Pegando os pedidos do usuario logado
		model.addAttribute("pedidos", pedidos);
		
		return "cliente-home";
	}
	
	

	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer clienteId = SecurityUtils.loggedCliente().getId(); // pegando o Id do cliente logado
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(); // .orElseThrow() -> Lan�a a excess�o
																				// caso o cliente n�o exista
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	

	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) { // Se n�o tiver erros salva o cliente
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!"); // Envia a mensagem de sucesso atrav�s de
																			// "msg"
			} catch (ValidationException e) {
                // rejectValue() -> rejeita a valida��o de um campo, no caso email, encaixa o erro com toda estrutura de erros criada com Model
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";

	}
	
	
	/**
	 * 
	 * @param filter
	 * @param command
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/search")
	public String search (@ModelAttribute("searchFilter") SearchFilter filter,
			              @RequestParam(value = "cmd", required = false) String command,
			              Model model) {
		/**
		 * N�o e feito o binding do cmd, ele n�o vai dentro do objeto, ele vai externamente e � recuperado atraves da request.
		 * Vai ter um valor na request do formulario que sera o cmd correspondente ao bot�o clicado, esse valor(cmd) sera extraido da requisi��o e sera passado como parametro na variavel command.
		 *  required = false -> Em alguns casos o cmd n�o obrigatorio, exemplo: na home do cliente fazendo pesquisa, nesse caso vira null e no processFilter ja � tratado . E necessario colocar o required como false para evitar erros, ja que spring entende por padr�o como obrigatorio. 
		 */
		filter.processFilter(command); // Processa o tipo de busca, por categoria ou texto e captura no command(cmd) selecionado na tela.
		
		List<Restaurante> restaurantes = restauranteService.search(filter); // Listando os restaurantes
		model.addAttribute("restaurantes", restaurantes);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model); // Listando as categorias
		
		model.addAttribute("searchFilter", filter); // recolocando dentro do model para que a proxima tela possa acessar
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep()); // Pegando o cliente logado e atribuindo o valor do cep
		
		return "cliente-busca";
	}
	
	
	
	@GetMapping(path = "/restaurante")
	public String viewRestaurante(
			@RequestParam("restauranteId") Integer restauranteId,
			@RequestParam(value = "categoria", required = false) String categoria,
			Model model) {
		
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		List<String> categorias = itemCardapioRepository.findCategorias(restauranteId);
		model.addAttribute("categorias", categorias);
		
		List<ItemCardapio> itensCardapioDestaque;
		List<ItemCardapio> itensCardapioNaoDestaque;
		
		if(categoria == null) { // Busca sem categoria
			
			itensCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);
			
			itensCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);
			
		} else { // Busca com categoria
			
			itensCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);
			
			itensCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
		}
		
		model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
		model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
		
		model.addAttribute("categoriaSelecionada", categoria);
		
		return "cliente-restaurante";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
