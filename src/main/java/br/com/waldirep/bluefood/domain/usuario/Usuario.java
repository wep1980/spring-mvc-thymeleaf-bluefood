package br.com.waldirep.bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // onlyExplicitlyIncluded = true -> Habilita por qual atributo o equals e hashcode sera chamado
@MappedSuperclass // Como essa classe nao e uma entidade, nao esta mapeada para uma tabela ela faz parte de um outra entidade(Cliente)
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include // Equals e hashcode -> comparação por ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O nome não pode ser vazio")
	@Size(max = 80, message = "O nome e muito grande")
	private String nome;
	
	@NotBlank(message = "O e-mail não pode ser vazio")
	@Size(max = 60, message = "O e-mail e muito grande")
	@Email(message = "O e-mail é inválido")
	private String email;
	
	@NotBlank(message = "A senha não pode ser vazia")
	@Size(max = 80, message = "A senha e muito grande")
	private String senha;
	
	@NotBlank(message = "O telefone não pode ser vazio")
	@Pattern(regexp = "[0-9]{10,11}", message = "O telefone possui formato inválido")
	@Column(length = 11, nullable = false) // O telefone e obrigatorio, validação de banco de dados
	private String telefone;

}
