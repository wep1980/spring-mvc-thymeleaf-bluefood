package br.com.waldirep.bluefood.domain.pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	
	// ?1 -> Primeiro parametro que vai ser passado que é o clienteId, ordenado por data em ordem decrescente
	@Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
	public List<Pedido> listPedidosByCliente(Integer clienteId);
	
	
	public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);
	
	
	/**
	 * Busca um pedido do restaurante
	 * @param pedidoId
	 * @param restauranteId
	 * @return
	 */
	public Pedido findByIdAndRestaurante_Id(Integer pedidoId, Integer restauranteId);
	
	
	/**
	 * Busca o restaurante de acordo com a data inicial e a data final dos pedidos
	 * @param restauranteId
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	@Query("SELECT p FROM Pedido p WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3")
	public List<Pedido> findByDateInterval(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	
	
	/**
	 * Metodo que busca um item do cardapio especifico de um restaurante, conta os itens e soma os preços em um intervalo de datas.
	 * Pega os itens do cardapio de um pedido, acessa os itens do ItemPedido (private Set<ItemPedido> itens;) com INNER JOIN p.itens --  GROUP BY i.itemCardapio.nome" -> agrupa os nomes dos itens com com a quantidade e valor.
	 * SUM(i.preco) -> Soma dos valores dos itens do pedido.
	 * AND p.data BETWEEN ?2 AND ?3 -> Intervelo de datas que sera digitado.
	 * COUNT(i.itemCardapio.id) -> O COUNT sempre retorna um Long
	 * O RETORNO e uma lista de array de Object -> List<Object[]> , o array vai ser o numero de colunas, na posição [0] = nome, na posição[1] = contagem dos itens, na posição[3] = a soma dos preços
	 * @param restauranteId
	 * @param itemCardapioId
	 * @return
	 */
	@Query("SELECT i.itemCardapio.nome, COUNT(i.itemCardapio.id), SUM(i.preco) FROM Pedido p INNER JOIN p.itens i WHERE p.restaurante.id = ?1 AND i.itemCardapio.id = ?2 AND p.data BETWEEN ?3 AND ?4 GROUP BY i.itemCardapio.nome")
	public List<Object[]> findItensForFaturamento(Integer restauranteId, Integer itemCardapioId, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	
	/**
	 * Metodo que busca os itens de um restaurante, soma a quantidade dos itens, soma o valor dos itens por quantidade do restaurante selecionado em um intervalo de tempo
	 * @param restauranteId
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	@Query("SELECT i.itemCardapio.nome, SUM(i.quantidade), SUM(i.preco * i.quantidade) FROM Pedido p INNER JOIN p.itens i WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3 GROUP BY i.itemCardapio.nome")
	public List<Object[]> findItensForFaturamento(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal );

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
