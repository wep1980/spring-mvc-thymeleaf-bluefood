package br.com.waldirep.bluefood.domain.pedido;

import java.math.BigDecimal;

import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
	
	
	@EqualsAndHashCode.Include
	private Integer id;
	
	private ItemCardapio itemCardapio;
	
	private Integer quantidade;
	
	private String observacoes;
	
	private BigDecimal preco;
	
	
	
	/**
	 * Calcula o valor total dos itens de pedido
	 * REGRA : preco * quantidade
	 * @return
	 */
	public BigDecimal getPrecoCalculado() {
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}
