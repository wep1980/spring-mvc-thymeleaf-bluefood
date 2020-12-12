package br.com.waldirep.bluefood.application;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waldirep.bluefood.domain.pedido.Carrinho;
import br.com.waldirep.bluefood.domain.pedido.ItemPedido;
import br.com.waldirep.bluefood.domain.pedido.ItemPedidoPK;
import br.com.waldirep.bluefood.domain.pedido.ItemPedidoRepository;
import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.Pedido.Status;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
import br.com.waldirep.bluefood.util.SecurityUtils;

@Service
public class PedidoService {

	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	/**
	 * Metodo que cria o pedido, paga o pedido e retorna o pedido.
	 * @param carrinho
	 * @param numCartao
	 * @return
	 */
	public Pedido criarEPagar(Carrinho carrinho, String numCartao) {
		
		Pedido pedido = new Pedido();
		
		pedido.setData(LocalDateTime.now()); // Capturando a hora do sistema
		pedido.setCliente(SecurityUtils.loggedCliente()); // Capturando o cliente logado
		pedido.setRestaurante(carrinho.getRestaurante()); // Capturando o restaurante que esta dentro do carrinho
		pedido.setStatus(Status.Producao); // Toda vez que um pedido e criado o status e produção
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega()); // Capturando a taxa de entrega que esta no carrinho, dentro de restaurante
		pedido.setSubtotal(carrinho.getPrecoTotal(false)); // Pegando o subTotal do carrinho sem a taxa de entrega
		pedido.setTotal(carrinho.getPrecoTotal(true)); // Pegando o valor total com a taxa de entrega
		
		/*
		 * Grava o pedido no BD e retorna a instancia que esta sendo gerenciada pela JPA, qq mudança nessa instancia aqui dentro do metodo vai ser reflito no BD no momento que o metodo terminar
		 */
		pedido = pedidoRepository.save(pedido);
		
		// Inserindo os itens do pedido
		int ordem = 1; // variavel de controle
		
		/*
		 * Para cada item de pedido do carrinho e gerado um Id.
		 * O ItemPedido não tem id gerado automaticamente, ele possui o id que é uma chave primaria composta, é preciso informar o id antes de gravar um item do pedido
		 */
		for (ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		return pedido;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
