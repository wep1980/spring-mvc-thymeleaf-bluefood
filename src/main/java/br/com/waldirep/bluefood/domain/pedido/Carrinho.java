package br.com.waldirep.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import lombok.Getter;


@Getter
public class Carrinho implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
	private Restaurante restaurante;
	
	
	/**
	 * Método que adiciona itens do cardapio no carrinho
	 * 
	 * REGRAS : No mesmo carrinho não é permitido misturar itens de restaurantes diferentes.
	 *          A partir do primeiro item colocado no carrinho o restaurante e selecionado e todos os itens so podem ser desse restaurante.
	 *          No carrinho não pode ser inserido o mesmo elemento.
	 * 
	 * @param itemCardapio
	 * @param quantidade
	 * @param observacoes
	 */
	public void adicionarItem(ItemCardapio itemCardapio, Integer quantidade, String observacoes) throws RestauranteDiferenteException{
		
		if(itens.size() == 0) { // Se o carrinho estiver vazio sera atribuido o restaurante no qual o primeiro item foi escolhido
			restaurante = itemCardapio.getRestaurante();
			
		/**
		 * Se ja possui um item adicionado e verificado se o item novo a ser inserido e do mesmo restaurante
		 * Se o restaurante do item que esta sendo inserido	for diferente do restaurante do primeiro item que ja foi inserido
		 */
		} else if (!itemCardapio.getRestaurante().getId().equals(restaurante.getId())) { 
			throw new RestauranteDiferenteException();
		}
		
		if(!exists(itemCardapio)) { // Se não existe o item de cardapio no carrinho, ele é ADICIONADO
			
			// No carrinho não pode ser inserido o mesmo elemento
			
			ItemPedido itemPedido = new ItemPedido();
			
			itemPedido.setItemCardapio(itemCardapio);
			itemPedido.setQuantidade(quantidade);
			itemPedido.setObservacoes(observacoes);
			
			// O preço do item do cardapio pode mudar, mas o preço do item do pedido tem que se manter da epoca da compra então essa informação e necessaria, pois o preço do pedido não pode ser alterado
			itemPedido.setPreco(itemCardapio.getPreco());
			
			itens.add(itemPedido);
		}
		
	}
	
	
	/**
	 * Metodo que recebe e adiciona um item de pedido.
	 * metodo que duplica um pedido
	 * Como ja e feita toda a validação no método adicionarItem() nao é necessario tratar exceção aqui
	 * @param itemPedido
	 */
	public void adicionarItem(ItemPedido itemPedido) {
		try {
			adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacoes());
		} catch (RestauranteDiferenteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void removerItem(ItemCardapio itemCardapio) {
	    
		for (Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();) {
			ItemPedido itemPedido = iterator.next();
			if(itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				iterator.remove();
				break; // Ja que não possui elementos de item de cardapio repetidos assim que ele encontrar o item não existe a necessidade de continuar procurando
			}
		}
		
		if(itens.size() == 0) { // Depois de remover o ultimno item do cardapio do carrinho, o carrinho pode ser utilizado por qq restaurante
			restaurante = null;
		}
	}
	
	
	
	/**
	 * Metodo que verifica e valida se ja existe o mesmo item de cardapio no carrinho
	 * @param itemCardapio
	 * @return
	 */
	private boolean exists(ItemCardapio itemCardapio) {
		
		/**
		 * Para cada item de pedido que existe no carrinho e comparado se ja existe o mesmo item do cardapio ja adicionado
		 */
		for (ItemPedido itemPedido : itens) { 
			if(itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				return true; // Se ja possui um item do cardapio no carrinho igual ao que esta sendo inserido returna true, POIS JA EXISTE O ITEM NO CARRINHO
			}
		}
		return false; // O item de cardapio ainda não existe no carrinho, retorna false
	}
	
	
	
	/**
	 * Metodo que calcula o valor total do carrinho
	 * @return
	 */
	public BigDecimal getPrecoTotal(boolean adicionarTaxaEntrega) {
		
		BigDecimal soma = BigDecimal.ZERO;
		
		for (ItemPedido item : itens) {
			soma = soma.add(item.getPrecoCalculado());
		}
		
		if(adicionarTaxaEntrega) { // Adiciona taxa de entrega
			soma = soma.add(restaurante.getTaxaEntrega());
		}
		
		return soma;
	}
	
	
	
	/**
	 * Limpa o carrinho
	 */
	public void limpar() {
		itens.clear();
		restaurante = null;
	}
	
	
	/**
	 * Metodo (Atalho) que indentifica se o carrinho esta vazio ou não 
	 * @return
	 */
	public boolean vazio() {
		return itens.size() == 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
