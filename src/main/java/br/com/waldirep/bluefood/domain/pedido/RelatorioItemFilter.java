package br.com.waldirep.bluefood.domain.pedido;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RelatorioItemFilter {

	
	private Integer itemId;
	
	/*
	 * No HTML5 o componente de calendario por padrão ele envia data no formato ANO-MES-DIA
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicial;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
	
	
}


