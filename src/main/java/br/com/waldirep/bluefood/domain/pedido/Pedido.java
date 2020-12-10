package br.com.waldirep.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "pedido")
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Quando o pedido e criado a primeira vez ele entra em produção
	 * @author wepbi
	 *
	 */
	public enum Status {
		Producao(1, "Em produção", false),
		Entrega(2, "Saiu para entrega", false),
		Concluido(3, "Concluído", true);
		
		Status(int ordem, String descricao, boolean ultimo){
			this.ordem = ordem;
			this.descricao = descricao;
			this.ultimo = ultimo;
		}
		
		
		int ordem;
		String descricao;
		boolean ultimo;
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private Status status;
	
	@NotNull
	private LocalDateTime data; // Classe do java com timeStamp completo
	
	@NotNull
	@ManyToOne // Um cliente pode ter muitos pedidos
	private Cliente cliente;
	
	@NotNull
	@ManyToOne // Um restaurante recebe varios pedidos
	private Restaurante restaurante;
	
	@NotNull 
	private BigDecimal subtotal; // Valor do pedido sem a taxa de entrega
	
	@NotNull 
	private BigDecimal total;
	
	@OneToMany(mappedBy = "id.pedido") // RELACIONAMENTO ENTRE CHAVES JPA -> A configuracão com itemPedido e pelo id da classe ItemPedido junto com o pedido da classe ItemPedidoPK = id.pedido
	private Set<ItemPedido> itens;
	

}
