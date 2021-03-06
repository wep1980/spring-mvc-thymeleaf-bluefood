package br.com.waldirep.bluefood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.waldirep.bluefood.domain.usuario.Usuario;
import br.com.waldirep.bluefood.infrastructure.web.validator.UploadConstraint;
import br.com.waldirep.bluefood.util.FileType;
import br.com.waldirep.bluefood.util.StringUtils;
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
	

	@NotBlank(message = "O CNPJ não pode ser vazio")
	@Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
	@Column(length = 14, nullable = false) // tamanho da coluna no banco de dados
	private String cnpj;
	
	@Size(max = 80)
	private String logotipo;
	
	@UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "O arquivo não é um arquivo de imagem válido") // Anota��o de valida��o customizada
	private transient MultipartFile logotipoFile; // transient -> o arquivo so fica armazenado em memoria, n�o armazena na tabela
	
	@NotNull(message = "A taxa de entrega não pode ser vazia")
	@Min(0) // Valor minimo de uma taxa de entrega 
	@Max(99) // Valor maximo de uma taxa de entrega 
	private BigDecimal taxaEntrega; // As vezes o arredondamento do double n�o fica muito bom
	
	@NotNull(message = "O tempo de entrega não pode ser vazio")
	@Min(0) // Valor minimo de uma taxa de entrega 
	@Max(120) // Valor maximo de uma taxa de entrega 
	private Integer tempoEntregaBase;
	
	
	/**
	 *  Na relac�o muitos para muitos um dos lados sera o dono do relacionamento(ele que configura o relacionamento). Em uma relac�o assim e gerada uma tabela extra
	 *  private Set<CategoriaRestaurante> categorias = new HashSet<>(0); -> Criar uma instancia de set, list, arrayList e sempre aconselhavel pois evita erros
	 *  Relacionamento Bidirecional, assim como de restaurante a gente consegue chegar em categorias, de categoria podemos chegar em restaurantes
	 */
	@ManyToMany(fetch = FetchType.EAGER) // Carrega as categorias ao buscar um restaurante
	@JoinTable(
			name = "restaurante_has_categoria",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
			)
	@Size(min = 1, message = "O restaurante precisa ter pelo ao menos uma categoria")
	@ToString.Exclude // Exclui do lombok da gera��o do toString a rela��o do restaurante com as categorias
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
	
	
	@OneToMany(mappedBy = "restaurante")
	private Set<ItemCardapio> itemCardapio = new HashSet<ItemCardapio>(0);
	
	
	
	
	 public void setLogotipoFileName() {
		 
		 if(getId() == null) {
			 throw new IllegalStateException("É preciso primeiro gravar o registro");
		 }
		 // "%04d" -> completa com 0 a esquerda ate que o tamanho fique 4
		 this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension()); //Forma o nome completo da imagem
	 }
	
	
	 
	 /**
	  * Metodo que retorna as categorias como texto
	  * Por ser um metodo getter ele pode ser chamado diretamente so pelo nome na pagina cliente-busca
	  * @return
	  */
	 public String getCategoriasAsText() {
		 
		 // LinkedHashSet -> Cole��o que mantem a ordem de inser��o e n�o permite elementos duplicados
		 Set<String> strings = new LinkedHashSet<String>();
		 
		 for(CategoriaRestaurante categoria : categorias) {
			 strings.add(categoria.getNome());
		 }
		 return StringUtils.concatenate(strings);
	 }
	 
	 /**
	  * A Regra de negocio � pegar cada digito do CEP somar todos e colocar no tempo de entrega base
	  * @param cep
	  * @return
	  */
	 public Integer calcularTempoEntrega(String cep) {
		 int soma = 0;
		 
		 // toCharArray() -> converte uma string em um array de caracteres
		 for(char c : cep.toCharArray()) {
			 
			 // Pegando o valor numerico do caracter
			 int valor = Character.getNumericValue(c);
			 if(valor > 0) {
				 soma += valor;
			 }
		 }
		 
		 soma /= 2;
		 
		 return tempoEntregaBase + soma;
	 }
	
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
