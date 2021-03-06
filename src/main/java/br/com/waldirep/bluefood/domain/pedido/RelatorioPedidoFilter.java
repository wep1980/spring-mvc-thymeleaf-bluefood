package br.com.waldirep.bluefood.domain.pedido;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data // Anota�ao do Lombok
public class RelatorioPedidoFilter {

	
	
	private Integer pedidoId;
	
	/*
	 * No HTML5 o componente de calendario por padr�o ele envia data no formato ANO-MES-DIA
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicial;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
}
