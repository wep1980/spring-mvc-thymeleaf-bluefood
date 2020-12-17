package br.com.waldirep.bluefood.application;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.waldirep.bluefood.domain.pedido.Carrinho;
import br.com.waldirep.bluefood.domain.pedido.ItemPedido;
import br.com.waldirep.bluefood.domain.pedido.ItemPedidoPK;
import br.com.waldirep.bluefood.domain.pedido.ItemPedidoRepository;
import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.Pedido.Status;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
import br.com.waldirep.bluefood.util.SecurityUtils;
import br.com.waldirep.domain.pagamento.DadosCartao;
import br.com.waldirep.domain.pagamento.StatusPagemento;

@Service
public class PedidoService {

	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	@Value("${bluefood.webservice.url}")
	private String webServiceURL;
	
	@Value("${bluefood.webservice.token}")
	private String webServiceToken;
	
	
	
	
	/**
	 * Metodo que cria o pedido, paga o pedido e retorna o pedido. Cria o pagamento
	 * 
	 * @param carrinho
	 * @param numCartao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class) // Se a exceção do tipo PagamentoException acontecer a transação sera desfeita por inteiro
	public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException{
		
		Pedido pedido = new Pedido();
		
		pedido.setData(LocalDateTime.now()); // Capturando a hora do sistema
		pedido.setCliente(SecurityUtils.loggedCliente()); // Capturando o cliente logado
		pedido.setRestaurante(carrinho.getRestaurante()); // Capturando o restaurante que esta dentro do carrinho
		pedido.setStatus(Status.Producao); // Toda vez que um pedido e criado o status e produção
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega()); // Capturando a taxa de entrega que esta no carrinho, dentro de restaurante
		pedido.setSubtotal(carrinho.getPrecoTotal(false)); // Pegando o subTotal do carrinho sem a taxa de entrega
		pedido.setTotal(carrinho.getPrecoTotal(true)); // Pegando o valor total com a taxa de entrega
		
		/*
		 * Grava o pedido no BD e retorna a instancia que esta sendo gerenciada pela JPA, qq mudança nessa instancia aqui dentro do metodo vai ser reflito no BD no momento que o metodo terminar
		 */
		pedido = pedidoRepository.save(pedido);
		
		// Inserindo os itens do pedido
		int ordem = 1; // variavel de controle
		
		/*
		 * Para cada item de pedido do carrinho e gerado um Id.
		 * O ItemPedido não tem id gerado automaticamente, ele possui o id que é uma chave primaria composta, é preciso informar o id antes de gravar um item do pedido
		 */
		for (ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		/*
		 * MultiValueMap -> é uma interface
		 * Inseri os headers
		 */
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Token", webServiceToken);
		
		// Intanscia que envia via HTTP para o servidor os dados do cartão e o token
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<DadosCartao>(dadosCartao, headers);
		
		//Objeto utilizado para fazer inovcacão de web service
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> response;
		
		try {
			/*
			 * Enviando os dados do carãto por POST : URL - webServiceURL, requestEntity, Tipo de resposta esperada Map.class -> Informando error ou status de retorno.
			 * Retorna a resposta do web service
			 */
			response = restTemplate.postForObject(webServiceURL, requestEntity, Map.class);
			
		} catch (Exception e) {
			
			throw new PagamentoException("Erro no servidor de pagamento");
		}
		
			// Pegando o status de pagamento que é retornado em forma de string e o valueOf transforma em um Enum de estado de pagamento
			StatusPagemento statusPagamento = StatusPagemento.valueOf(response.get("status"));
			
			if(statusPagamento != StatusPagemento.Autorizado) {
				throw new PagamentoException(statusPagamento.getDescricao());
			
		}
		
		return pedido;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
