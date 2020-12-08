package br.com.waldirep.bluefood.domain.restaurante;

import lombok.Data;

/**
 * Classe que faz o binding no formulario de busca
 * 
 * @author wepbi
 *
 */
@Data //LOMBOK -> Coloca ao mesmo tempo @getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
public class SearchFilter {
	
	
	/**
	 * Determina se a procura e por texto ou categoria
	 * A ação do usuario que vai decidir qual e o tipo de informação, se a procura for pela caixa de texto o tipo sera texto, se a procura for clicando em categorias o searchType tem que ser igual a categoria
	 * @author wepbi
	 *
	 */
	public enum SearchType{
		Texto, Categoria
	}

	
	private String texto;
	
	private SearchType searchType;
	
	private Integer categoriaId;
	
	
	/**
	 * Metodo que processa a informação do tipo de busca, texto ou categoria
	 */
	public void processFilter() {
		
		/*
		 * Se o searchType for texto eu seto a categoriaId como null, para evitar que fique um valor setado de uma busca anterior.
		 * Se o searchType for categoria eu seto o texto como null, para evitar que fique um valor setado de uma busca anterior
		 */
		if(searchType == SearchType.Texto) {
			categoriaId = null;
		} else if (searchType == SearchType.Categoria){
			texto = null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
