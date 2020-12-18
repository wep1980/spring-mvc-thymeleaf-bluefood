package br.com.waldirep.bluefood.domain.pedido;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe que faz a liga��o entre os os resultados do SQL a tela de vizualiza��o
 * classe que contem as informa��es que ser�o exibidas
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
