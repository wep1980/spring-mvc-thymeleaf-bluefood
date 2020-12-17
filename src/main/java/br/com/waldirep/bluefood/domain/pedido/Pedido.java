package br.com.waldirep.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.pagamento.Pagamento;
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
		
		
		public String getDescricao() {
			return descricao;
		}
		
		public int getOrdem() {
			return ordem;
		}
		
		public boolean isUltimo() {
			return ultimo;
		}
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
	@Column(name = "taxa_entrega")
	private BigDecimal taxaEntrega;
	
	@NotNull 
	private BigDecimal total;
	
	//fetch = FetchType.EAGER -> Carregar os itens do pedidos
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER) // RELACIONAMENTO ENTRE CHAVES JPA -> A configuracão com itemPedido e pelo id da classe ItemPedido junto com o pedido da classe ItemPedidoPK = id.pedido
	private Set<ItemPedido> itens;
	
	/*
	 * Como na classe Pagamento foi eleita a dona da relação, aqui e necessario colocar o mappedBy referenciando o atributo(pedido) da dona do relacionamento, classe Pagamento
	 */
	@OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;
	
	
	/**
	 * Metrodo que retorna o Id formatado para a tela de cliente-home no campo ultimos pedidos
	 * @return
	 */
	public String getFormattedId() {
		return String.format("#%04d", id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
