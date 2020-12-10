package br.com.waldirep.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



/**
 * O Id desta classe sera composta, ao trabalhar com chave composta e necessario criar uma classe (ItemPedidoPK)
 * @author wepbi
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @EmbeddedId // Serve para indicar que as 2 colunas da chave composta faça parte da tabela
    @EqualsAndHashCode.Include
	private ItemPedidoPK id; //Foi criada uma classe para representar o ID pq ele e composto.  Na hora de gerar a tabela o Id vai se transofrmar em 2 colunas
	
	@NotNull
	@ManyToOne // Um item do cardapio tem em muitos pedidos
	private ItemCardapio itemCardapio;
	
	@NotNull
	private Integer quantidade;
	
	@Size(max = 50)
	private String observacoes;
	
	@NotNull
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
