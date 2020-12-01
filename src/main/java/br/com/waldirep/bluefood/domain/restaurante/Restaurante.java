package br.com.waldirep.bluefood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.waldirep.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)// callSuper = true -> chama o equals e hashcode da super classe
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	

	@NotBlank(message = "O CNPJ n�o pode ser vazio")
	@Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inv�lido")
	@Column(length = 14, nullable = false) // tamanho da coluna no banco de dados
	private String cnpj;
	
	@Size(max = 80)
	private String logotipo;
	
	@NotNull(message = "A taxa de entrega n�o pode ser vazia")
	@Min(0) // Valor minimo de uma taxa de entrega 
	@Max(99) // Valor maximo de uma taxa de entrega 
	private BigDecimal taxaEntrega; // As vezes o arredondamento do double n�o fica muito bom
	
	@NotNull(message = "O tempo de entrega n�o pode ser vazio")
	@Min(0) // Valor minimo de uma taxa de entrega 
	@Max(120) // Valor maximo de uma taxa de entrega 
	private Integer tempoEntregaBase;
	
	
	/**
	 *  Na relac�o muitos para muitos um dos lados sera o dono do relacionamento(ele que configura o relacionamento). Em uma relac�o assim e gerada uma tabela extra
	 *  private Set<CategoriaRestaurante> categorias = new HashSet<>(0); -> Criar uma instancia de set, list, arrayList e sempre aconselhavel pois evita erros
	 *  Relacionamento Bidirecional, assim como de restaurante a gente consegue chegar em categorias, de categoria podemos chegar em restaurantes
	 */
	@ManyToMany
	@JoinTable(
			name = "restaurante_has_categoria",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
			)
	@Size(min = 1, message = "O restaurante precisa ter pelo ao menos uma categoria")
	@ToString.Exclude // Exclui do lombok da gera��o do toString a rela��o do restaurante com as categorias
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
}
