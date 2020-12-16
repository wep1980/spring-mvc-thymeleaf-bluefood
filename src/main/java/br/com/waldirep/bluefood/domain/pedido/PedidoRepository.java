package br.com.waldirep.bluefood.domain.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	
	// ?1 -> Primeiro parametro que vai ser passado que é o clienteId, ordenado por data em ordem decrescente
	@Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
	public List<Pedido> listPedidosByCliente(Integer clienteId);

}
