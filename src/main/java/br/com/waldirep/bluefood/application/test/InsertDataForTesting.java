package br.com.waldirep.bluefood.application.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.waldirep.bluefood.domain.cliente.Cliente;
import br.com.waldirep.bluefood.domain.cliente.ClienteRepository;
import br.com.waldirep.bluefood.domain.pedido.Pedido;
import br.com.waldirep.bluefood.domain.pedido.Pedido.Status;
import br.com.waldirep.bluefood.domain.pedido.PedidoRepository;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.waldirep.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapio;
import br.com.waldirep.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.waldirep.bluefood.domain.restaurante.Restaurante;
import br.com.waldirep.bluefood.domain.restaurante.RestauranteRepository;
import br.com.waldirep.bluefood.util.StringUtils;

@Component// Colocando a classe dentro do contexto do spring
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRespository;
	
	

	@EventListener // Ao iniciar a aplica��o o metodo sera invocado apos a inicializa��o
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		Cliente[] clientes = clientes();
		Restaurante[] restaurantes = restaurantes();
		itensCardapio(restaurantes);
		
		Pedido p = new Pedido();
		p.setData(LocalDateTime.now());
		p.setCliente(clientes[0]);
		p.setRestaurante(restaurantes[0]);
		p.setStatus(Status.Producao);
		p.setSubtotal(BigDecimal.valueOf(10));
		p.setTaxaEntrega(BigDecimal.valueOf(2));
		p.setTotal(BigDecimal.valueOf(12.0));
		
		Pedido p2 = new Pedido();
		p2.setData(LocalDateTime.now());
		p2.setCliente(clientes[0]);
		p2.setRestaurante(restaurantes[0]);
		p2.setStatus(Status.Producao);
		p2.setSubtotal(BigDecimal.valueOf(11));
		p2.setTaxaEntrega(BigDecimal.valueOf(3));
		p2.setTotal(BigDecimal.valueOf(13.0));
		
		Pedido p3 = new Pedido();
		p3.setData(LocalDateTime.now());
		p3.setCliente(clientes[0]);
		p3.setRestaurante(restaurantes[0]);
		p3.setStatus(Status.Producao);
		p3.setSubtotal(BigDecimal.valueOf(12));
		p3.setTaxaEntrega(BigDecimal.valueOf(4));
		p3.setTotal(BigDecimal.valueOf(14.0));
		
		Pedido p4 = new Pedido();
		p4.setData(LocalDateTime.now());
		p4.setCliente(clientes[0]);
		p4.setRestaurante(restaurantes[0]);
		p4.setStatus(Status.Producao);
		p4.setSubtotal(BigDecimal.valueOf(13));
		p4.setTaxaEntrega(BigDecimal.valueOf(5));
		p4.setTotal(BigDecimal.valueOf(15.0));
		
		pedidoRespository.save(p);
		pedidoRespository.save(p2);
		pedidoRespository.save(p3);
		pedidoRespository.save(p4);
	}
	
	
	private Restaurante[] restaurantes() {
		List<Restaurante> restaurantes = new ArrayList<>(); 
		
		// Buscando as categorias por ID
		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow(NoSuchElementException::new);
		
		Restaurante r = new Restaurante();
		r.setNome("Bubger King");
		r.setEmail("r1@bluefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000101");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("99876671010");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Mc Naldo's");
		r.setEmail("r2@bluefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000102");
		r.setTaxaEntrega(BigDecimal.valueOf(4.5));
		r.setTelefone("99876671011");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		r.setTempoEntregaBase(25);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Sbubby");
		r.setEmail("r3@bluefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000103");
		r.setTaxaEntrega(BigDecimal.valueOf(12.2));
		r.setTelefone("99876671012");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		r.setTempoEntregaBase(38);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Pizza Brut");
		r.setEmail("r4@bluefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000104");
		r.setTaxaEntrega(BigDecimal.valueOf(9.8));
		r.setTelefone("99876671013");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0004-logo.png");
		r.setTempoEntregaBase(22);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Wiki Japa");
		r.setEmail("r5@bluefood.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000105");
		r.setTaxaEntrega(BigDecimal.valueOf(14.9));
		r.setTelefone("99876671014");
		r.getCategorias().add(categoriaJapones);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0005-logo.png");
		r.setTempoEntregaBase(19);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()]; 
		return restaurantes.toArray(array);
	}
	
	
	
	private Cliente[] clientes() {
		List<Cliente> clientes = new ArrayList<>(); 
		
		Cliente c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joao@bluefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("89300100");
		c.setCpf("03099887666");
		c.setTelefone("99355430001");
		clientes.add(c);
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("maria@bluefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("89300101");
		c.setCpf("03099887677");
		c.setTelefone("99355430002");
		clientes.add(c);
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()]; 
		return clientes.toArray(array);
	}
	
	
	private void itensCardapio(Restaurante[] restaurantes) {
		ItemCardapio ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Delicioso sanduíche com molho");
		ic.setNome("Double Cheese Burger Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche padrão que mata a fome");
		ic.setNome("Cheese Burger Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche natural com peito de peru");
		ic.setNome("Sanduíche Natural da Casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gás");
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural e docinho");
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setNome("Pizza de Calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
	}
}
