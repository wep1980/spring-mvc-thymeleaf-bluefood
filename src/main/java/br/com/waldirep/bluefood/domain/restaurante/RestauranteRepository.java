package br.com.waldirep.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer>{
	
	
	
	public Restaurante findByEmail(String email);
	
	
	/**
	 * Metodo que retorna a lista de restaurantes ignorando se é letra maiuscula ou minuscula por parte do nome
	 * @param nome
	 * @return
	 */
	public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);
	
	
	/**
	 * Metodo que retorna todas as categorias de acordo com o restaurante selecionado
	 * Procura pelo id da categoria -> Categorias_Id
	 * @param categoriaId
	 * @return
	 */
	public List<Restaurante> findByCategorias_Id(Integer categoriaId);

}
