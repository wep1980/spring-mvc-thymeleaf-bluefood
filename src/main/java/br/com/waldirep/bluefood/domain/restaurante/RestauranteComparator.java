package br.com.waldirep.bluefood.domain.restaurante;

import java.util.Comparator;

import br.com.waldirep.bluefood.domain.restaurante.SearchFilter.Order;

/**
 * Compara os Restaurantes para saber se vai antes ou depois na cole��o
 * @author wepbi
 *
 */
public class RestauranteComparator implements Comparator<Restaurante>{

	
	private SearchFilter filter;
	
	private String cep;
	
	
	
	
	public RestauranteComparator(SearchFilter filter, String cep) {
		this.filter = filter;
		this.cep = cep;
	}




	/*
	 * Processo de ordena��o na lista 
	 * compareTo() -> por padr�o trabalham em ordem crescente
	 */
	@Override
	public int compare(Restaurante r1, Restaurante r2) {
		int result;
		
		if(filter.getOrder() == Order.Taxa) { // Se for uma ordena��o por taxa
			result = r1.getTaxaEntrega().compareTo(r2.getTaxaEntrega());
			
		} else if (filter.getOrder() == Order.Tempo) {
			result = r1.calcularTempoEntrega(cep).compareTo(r2.calcularTempoEntrega(cep));
			
		} else {
			throw new IllegalStateException("O valor de ordena��o " + filter.getOrder() + " n�o � v�lido");
		}
		
		return filter.isAsc() ? result : -result; // Se o resultado for ascendente(crescente) retorna o proprio resultado, sen�o o descendente(decrescente) � revertido o sinal e retorna crescente. + crescente, - decrescente, 0 se for ==
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
