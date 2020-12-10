package br.com.waldirep.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer>{
	
	
	/*
	 * Metodo que traz as categorias do restaurante selecionado.
	 * JPQL -> Trabalha com classes e atributos.
	 * ?1 -> Pega o primneiro parametro passado no metodo e comparara com o restauranteId
	 */
	@Query("SELECT DISTINCT ic.categoria FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
	public List<String> findCategorias(Integer restauranteId);
	
	
	
	/**
	 * Pesquisa os items do cardapio pelo Id do restaurante e ordena por nome.
	 * Lista sem destaque
	 * @param restauranteId
	 * @return
	 */
	public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);
	
	
	/**
	 * Pesquisa os items do cardapio por destaque ou sem destaque pelo Id do restaurante e ordena por nome.
	 * @param restauranteId
	 * @param destaque -> indica se os items trazidos sao com destaque ou sem destaque
	 * @return
	 */
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer restauranteId, boolean destaque);
	
	
	/**
	 * Pesquisa os items do cardapio por destaque ou sem destaque pelo Id do restaurante e pela categoria do itemCardapio e ordena por nome.
	 * @param restauranteId
	 * @param destaque
	 * @param categoria
	 * @return
	 */
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer restauranteId, boolean destaque, String categoria);

}
