package br.com.waldirep.bluefood.domain.restaurante;

import lombok.Data;

/**
 * Classe que faz o binding no formulario de busca
 * @author wepbi
 *
 */
@Data //LOMBOK -> Coloca ao mesmo tempo @getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
public class SearchFilter {

	
	public String texto;
}
