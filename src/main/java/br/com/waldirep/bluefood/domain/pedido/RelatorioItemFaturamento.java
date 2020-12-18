package br.com.waldirep.bluefood.domain.pedido;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe que faz a ligação entre os os resultados do SQL a tela de vizualização
 * classe que contem as informações que serão exibidas
 * @author wepbi
 *
 */
@Getter
@AllArgsConstructor
public class RelatorioItemFaturamento {

	
	
	private String nome;
	
	private Long quantidade;
	
	private BigDecimal valor;
	

}
