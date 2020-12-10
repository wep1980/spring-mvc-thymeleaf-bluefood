package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.waldirep.bluefood.domain.pedido.Carrinho;
import br.com.waldirep.bluefood.domain.pedido.RestauranteDiferenteException;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapioRepository;

/**
 * Controller que vai realizar todas as opera��es do carrinho
 * @author wepbi
 *
 */
@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho") // Atributos de sess�o, continuam no Model mesmo ap�s varias requisi��es
public class CarrinhoController {

	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	
	
	/**
	 * Metodo que cria um carrinho novo
	 * 
	 * REGRA : Quando for necessario acessar o atributo carrinho que esteja no model, se ele n�o existir o spring vai utilizar esse metodo para criar o carrinho, assim garante que o carrinho vai existir desde o primeiro acesso
	 * @return
	 */
	@ModelAttribute("carrinho") 
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	
	/**
	 * Metodo que adiciona itens no carrinho
	 * O carrinho precisa estar na sess�o do usuario
	 * @param itemId
	 * @param quantidade
	 * @param observacoes
	 * @return
	 */
	@GetMapping(path = "/adicionar")
	public String adicionarItem(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("quantidade") Integer quantidade,
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
		
		try {
			carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
		} catch (RestauranteDiferenteException e) {
			model.addAttribute("msg", "N�o � poss�vel misturar comidas de restaurantes diferentes");
		}
		
		return "cliente-carrinho";
	}

}
