package br.com.waldirep.bluefood.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
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

}
