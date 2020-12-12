package br.com.waldirep.bluefood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

// ItemPedidoPK -> e o tipo da chave primaria, ItemPedido possui uma chave composta
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK>{

}
