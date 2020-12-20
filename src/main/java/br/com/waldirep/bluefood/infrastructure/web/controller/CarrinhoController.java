package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.waldirep.bluefood.domain.pedido.Carrinho;
import br.com.waldirep.bluefood.domain.pedido.ItemPedido;
import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	
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
	
	
	
	@GetMapping(path = "/visualizar")
	public String viewCarrinho() {
		return "cliente-carrinho";
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
			model.addAttribute("msg", "Não é possível misturar comidas de restaurantes diferentes");
		}
		
		return "cliente-carrinho";
	}
	
	
	
	
	@GetMapping(path = "/remover")
	public String removerItem(
			@RequestParam("itemId") Integer itemId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
	
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
	
	    carrinho.removerItem(itemCardapio);
	
	    if(carrinho.vazio()) {
	    	sessionStatus.setComplete(); // elimina da sess�o os atributos que est�o no carrinho
	    }
	    return "cliente-carrinho";
	}
	
	
	
	@GetMapping(path = "/refazerCarrinho")
	public String refazerCarrinho(
			@RequestParam("pedidoId") Integer pedidoId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		
		carrinho.limpar();
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			carrinho.adicionarItem(itemPedido);
		}
		return "cliente-carrinho";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
