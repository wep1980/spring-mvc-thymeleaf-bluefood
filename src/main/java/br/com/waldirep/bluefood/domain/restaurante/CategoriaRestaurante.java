package br.com.waldirep.bluefood.domain.restaurante;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categoria_restaurante")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoriaRestaurante implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include // O Id e utilizado para verificar os elementos no equals e hashcode
	private Integer id;
	
	@NotNull
	@Size(max = 20)
	private String nome;
	
	@NotNull
	@Size(max = 50)
	private String imagem;
	
	
	@ManyToMany(mappedBy = "categorias") // Foi configurado(mapeado) na classe que e a dona do relacionamento - Restaurante
	private Set<Restaurante> restaurantes = new HashSet<>(0);

}
