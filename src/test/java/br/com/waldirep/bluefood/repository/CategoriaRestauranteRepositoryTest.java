package br.com.waldirep.bluefood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;

/**
 * Classe de testes do Repositorio categoriaRestaurante
 * @author wepbi
 *
 */
@DataJpaTest // Coloca a classe dentro do contexto de testes do Spring
@ActiveProfiles("test") // Utiliza-ra o profile de testes que utiliza o banco de dados H2
public class CategoriaRestauranteRepositoryTest {

	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	
	/**
	 * Requisitos basicos para o método de teste : public - O retorno tem que ser void - E ele pode lançar excessoes
	 */
	@Test
	public void testInsertAndDelete() throws Exception {
		
		assertThat(categoriaRestauranteRepository).isNotNull(); // Garante que o categoriaRestauranteRepository não esteja NULO
		
		CategoriaRestaurante cr = new CategoriaRestaurante();
		cr.setNome("Chinesa");
		cr.setImagem("chinesa.png");
		
		categoriaRestauranteRepository.saveAndFlush(cr); // Salva imediatamente no banco de dados
		
		// Verificar se o ID e diferente de NUll
		
		/**
		 * Metodo da classe Assertions
		 * import static org.assertj.core.api.Assertions.assertThat; -> Importe static da classe, evita ficar o tempo todo referenciando a classe
		 * assertThat -> TRADUCÃO - GARANTA QUE 
		 */
		assertThat(cr.getId()).isNotNull(); // Garante que o ID não é NULO
		
		// Buscando a categoria inserida no banco de dados
		CategoriaRestaurante cr2 = categoriaRestauranteRepository.findById(cr.getId()).orElseThrow(NoSuchElementException::new);
		
		assertThat(cr.getNome()).isEqualTo(cr2.getNome()); // Testando para verificar se os nomes são iguais
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(7); // Essa categoria inserida e a 7 no banco de dados, Verifica se existe 7 categorias na lista
		
		categoriaRestauranteRepository.delete(cr); // Apagando a categoria criada no teste
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(6); // Verifica se existe 6 categorias apos a deleção da categoria acima
	}

}
