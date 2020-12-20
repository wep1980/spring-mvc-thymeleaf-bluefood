package br.com.waldirep.bluefood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.waldirep.bluefood.infrastructure.web.validator.UploadConstraint;
import br.com.waldirep.bluefood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_cardapio")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemCardapio implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@javax.validation.constraints.NotBlank(message = "O nome não pode ser vazio")
	@Size(max = 50)
	private String nome;
	
	@javax.validation.constraints.NotBlank(message = "A categoria não pode ser vazia")
	@Size(max = 25)
	private String categoria;
	
	@javax.validation.constraints.NotBlank(message = "A descrição não pode ser vazia")
	@Size(max = 80)
	private String descricao;
	
	@Size(max = 80)
	private String imagem;
	
	@NotNull(message = "O preço não pode ser vazio")
	@Min(0)
	private BigDecimal preco;
	
	@NotNull
	private boolean destaque;
	
	@NotNull
	@JoinColumn(name = "restaurante_id")
	@ManyToOne // (O lado onde fica o Many manda na rela��o. Regra da JPA) Um restaurante tem muitos itens de cardapio, O dono da rela��o e o itemCardapio -- Na rela��o ManyToMany pode ser qualquer lado o dono da rela��o
	private Restaurante restaurante;
	
	@UploadConstraint(acceptedTypes = FileType.PNG, message = "O arquivo não � um arquivo de imagem v�lida")
	private transient MultipartFile imagemFile;
	
	
	
	
	public void setImagemFileName() {
		if(getId() == null) {
			throw new IllegalStateException("O objeto precisa primeiro ser criado");
		}
		
		this.imagem = String.format("%04d-comida.%s", getId(), FileType.of(imagemFile.getContentType()).getExtension());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
