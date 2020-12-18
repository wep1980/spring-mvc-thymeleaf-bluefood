package br.com.waldirep.bluefood.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
import br.com.waldirep.bluefood.domain.pedido.RelatorioItemFaturamento;
import br.com.waldirep.bluefood.domain.pedido.RelatorioItemFilter;
import br.com.waldirep.bluefood.domain.pedido.RelatorioPedidoFilter;

@Service
public class RelatorioService {

	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	
	
	public List<Pedido> listPedidos(Integer restauranteId, RelatorioPedidoFilter filter){
		
		Integer pedidoId = filter.getPedidoId();
		
		if(pedidoId != null) {
		   Pedido pedido = pedidoRepository.findByIdAndRestaurante_Id(pedidoId, restauranteId);
		   return List.of(pedido); // Busca na lista por um pedido unico
		}
		
		LocalDate dataInicial =  filter.getDataInicial();
		LocalDate dataFinal =  filter.getDataFinal();
		
		if(dataInicial == null) {
			return List.of(); // Se não houver data inicial retorna uma lista vazia
		}
		
		if(dataFinal == null) {
			dataFinal = LocalDate.now(); // Se não houver data final a data final sera a atual
		}
		
		// dataInicial.atStartOfDay() -> retorna um LocalDateTime a partir do começo do dia e na dataFinal pega o final do dia -> dataFinal.atTime(23, 59, 59)
		return pedidoRepository.findByDateInterval(restauranteId, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));
	}
	
	
	
	
	public List<RelatorioItemFaturamento> calcularFaturamentoItens (Integer restauranteId, RelatorioItemFilter filter){
		
		List<Object[]> itensObj;
		
		Integer itemId = filter.getItemId();
		
		LocalDate dataInicial =  filter.getDataInicial();
		LocalDate dataFinal =  filter.getDataFinal();
		
		if(dataInicial == null) {
			return List.of(); // Se não houver data inicial retorna uma lista vazia
		}
		
		if(dataFinal == null) {
			dataFinal = LocalDate.now(); // Se não houver data final a data final sera a atual
		}
		
		LocalDateTime dataHoraInicial = dataInicial.atStartOfDay();
		LocalDateTime dataHoraFinal = dataFinal.atTime(23, 59, 59);
		
		if(itemId != 0) { // Pega o item e o restaurante selecionado
			itensObj = pedidoRepository.findItensForFaturamento(restauranteId, itemId, dataHoraInicial, dataHoraFinal);
			
		} else { // Pega todos os itens do restaurante selecionado
			itensObj = pedidoRepository.findItensForFaturamento(restauranteId, dataHoraInicial, dataHoraFinal);
		}
		
		List<RelatorioItemFaturamento> itens = new ArrayList<RelatorioItemFaturamento>();
		
		/*
		 * Variaveis criadas de acordo com o resultado que vem da QUERY do PedidoRespository, que realiza um COUNT = quantidade, e tambem faz um SUN(soma) do preco dos ItemCardapio = valor
		 */
		for (Object[] item : itensObj) {
			String nome = (String) item[0]; // (String) ->Transforma de Object para String
			Long quantidade = (Long) item[1]; // (Long) ->Transforma de Object para Long
			BigDecimal valor = (BigDecimal) item[2]; // (BigDecimal) -> Transforma de Object para BigDecimal
			
			itens.add(new RelatorioItemFaturamento(nome, quantidade, valor));
		}
		return itens;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
