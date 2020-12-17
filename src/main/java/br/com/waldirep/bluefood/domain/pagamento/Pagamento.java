package br.com.waldirep.bluefood.domain.pagamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.waldirep.bluefood.domain.pedido.Pedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY -> não e necesserio usar pois o id vai vir da classe Pedido
	private Integer id;
	
	/*
	 * Esse lado e o dono do relacionamenrto.
	 * Existe um relacionamento bi direcional com a classe Pedido, embora não seja necessario.
	 * É comum em relacionamentos OneToOne que as tebelas sejam relacionadas com a mesma chave primaria
	 */
	@NotNull
	@OneToOne
	@MapsId // Pega o Id do outro lado da relação na classe Pedido e utiliza no id aqui da classe private Integer id - UTILIZAÇÃO DA MESMA CHAVE PRIMARIA NAS 2 CLASSES
	private Pedido pedido;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	@Size(min = 4, max = 4)
	@Column(name = "num_cartao_final")
	private String numCartaoFinal;
	
	/*
	 * Quando ocorre mapeamento com ENUM a JPA cria uma coluna com valores numeros inteiros e utilizar o indice do enum como valor
	 */
	@Enumerated(EnumType.STRING) // Pega o texto no enum, pode ser pegar o numero também
	@Column(nullable = false, length = 10)
	private BandeiraCartao bandeiraCartao;
	
	
	
	
	/**
	 * Metodo que define o numero final do cartao e a bandeira
	 * 
	 * REGRAS : Pega os 4 ultimos digitos do cartão
	 * @param numCartao
	 */
	public void definirNumeroEBandeira(String numCartao) {
		
		// Pega o numero do cartão da posição 12 em diante, como o cartao tem 16 digitos ele vai pegar os 4 ultimos
		numCartaoFinal = numCartao.substring(12);
		bandeiraCartao = getBandeira(numCartao);
	}
	
	
	
	/**
	 * Metodo que define qual e a bandeira do cartao baseado no numero
	 * @param numCartao
	 * @return
	 */
	private BandeiraCartao getBandeira(String numCartao) {
		if(numCartao.startsWith("0000")) {
			return BandeiraCartao.AMEX;
		}
		
		if(numCartao.startsWith("1111")) {
			return BandeiraCartao.ELO;
		}
		
		if(numCartao.startsWith("2222")) {
			return BandeiraCartao.MASTER;
		}
		
		return BandeiraCartao.VISA;
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
