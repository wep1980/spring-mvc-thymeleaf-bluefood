package br.com.waldirep.bluefood.domain.cliente;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.waldirep.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)// callSuper = true -> chama o equals e hashcode da super classe
@Entity
public class Cliente extends Usuario{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O CPF n�o pode ser vazio")
	@Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inv�lido")
	@Column(length = 11, nullable = false) // tamanho da coluna no banco de dados
	private String cpf;
	
	@NotBlank(message = "O CEP n�o pode ser vazio")
	@Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato inv�lido")
	@Column(length = 8, nullable = false)
	private String cep;

}
