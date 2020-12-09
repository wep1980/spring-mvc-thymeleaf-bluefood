package br.com.waldirep.bluefood.domain.restaurante;

import br.com.waldirep.bluefood.util.StringUtils;
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

	public enum Order {
		Taxa, Tempo;
	}
	
	public enum Command {
		EntregaGratis, MaiorTaxa, MenorTaxa, MaiorTempo, MenorTempo;
	}
	
	//----------------------------------------------- CAMPOS DE BUSCA -----------------------------------------------------------------------------
	
	private String texto;
	
	private SearchType searchType;
	
	private Integer categoriaId;
	
	//------------------------------------------------ INICIO DOS FILTROS ------------------------------------------------------------------------------------------------------------------------
	
	// A taxa e criada na geração do objeto
	private Order order = Order.Taxa;
	
	private boolean asc;
	
	// Por padrao e false
	private boolean entregaGratis;
	
	
	
	
	/**
	 * Metodo que processa a informação do tipo de busca, texto ou categoria
	 */
	public void processFilter(String cmdString) {
		
		if(!StringUtils.isEmpty(cmdString)) { // Se o cmdString não for vazio
			
			// Command.valueOf(cmdString); -> Todos os enums possuem esse metodo que vc passa uma string e ele retorna o enum associado. O nome submetido no front end tem que ser igual ao que esta aqui
			Command cmd = Command.valueOf(cmdString);
			
			if(cmd == Command.EntregaGratis) {
				
				/**
				 * Se a entrega gratis estava marcada, a entrega gratis foi clicada então ela precisa ser desmarcada( se era true passa a ser false ) assim muda o estado no objeto, quando a pagina e direcionada no binding e verificado que é falso e desmarca o botão
				 * RESUMO : EM UMA NOVA REQUISIÇÃO DA PÁGINA O BOTÃO ESTARA DESMARCADO, MOSTRANDO SEU ESTADO REAL
				 */
				entregaGratis = !entregaGratis;
				
			} else if (cmd == Command.MaiorTaxa) {
				order = Order.Taxa;
				asc = false;
				
			} else if (cmd == Command.MenorTaxa) {
				order = Order.Taxa;
				asc = true;
				
			} else if (cmd == Command.MaiorTempo) {
				order = Order.Tempo;
				asc = false;
				
			} else if (cmd == Command.MenorTempo) {
				order = Order.Tempo;
				asc = true;
				
			} 
		}
		
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
