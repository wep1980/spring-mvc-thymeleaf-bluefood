package br.com.waldirep.bluefood.domain.pedido;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Classe que representa a chave primaria composta
 * @author wepbi
 *
 */
@Getter
@AllArgsConstructor // Construtor com todos os parametros LOMBOK
@NoArgsConstructor // Construtor sem parametros LOMBOK
@EqualsAndHashCode
@Embeddable 
public class ItemPedidoPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@ManyToOne 
	private Pedido pedido; // pedido alem de fazer parte da chave primaria ele é uma chave estrangeira para tabela de pedido -- O lado many e o dono do relacionamento
	
	@NotNull
	private Integer ordem;

}
