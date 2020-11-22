package br.com.waldirep.bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	private String telefone;

}
