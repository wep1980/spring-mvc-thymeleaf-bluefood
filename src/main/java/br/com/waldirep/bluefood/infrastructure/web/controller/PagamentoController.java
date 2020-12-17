package br.com.waldirep.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.waldirep.bluefood.application.service.PagamentoException;
import br.com.waldirep.bluefood.application.service.PedidoService;
import br.com.waldirep.bluefood.domain.pedido.Carrinho;
import br.com.waldirep.bluefood.domain.pedido.Pedido;

/**
 * Controller que vai realizar todas as operações com imagens
 * @author wepbi
 *
 */
@Controller
@RequestMapping("/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {

	
	@Autowired
	private PedidoService pedidoService;
	
	
	@PostMapping(path = "/pagar")
	public String pagar(
			@RequestParam("numCartao") String numCartao, 
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		
		try {
			Pedido pedido = pedidoService.criarEPagar(carrinho, numCartao); // Depois de ser gerado o pedido do carrinho, o carrinho precisa ficar vazio
			sessionStatus.setComplete();
			
			/*
			 * Direciona o usuario para a tela de pedido
			 * Ao inves de ser feito forward sera feito um redirect -> o navegador redireciona
			 */
			return "redirect:/cliente/pedido/view?pedidoId=" + pedido.getId();
			
		} catch (PagamentoException e) {
			model.addAttribute("msg", e.getMessage());
			return "cliente-carrinho";
		}
	}

}
