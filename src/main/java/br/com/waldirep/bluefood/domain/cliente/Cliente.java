package br.com.waldirep.bluefood.domain.cliente;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "cliente")
public class Cliente extends Usuario{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O CPF não pode ser vazio")
	@Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
	@Column(length = 11, nullable = false) // tamanho da coluna no banco de dados
	private String cpf;
	
	@NotBlank(message = "O CEP não pode ser vazio")
	@Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato inválido")
	@Column(length = 8, nullable = false)
	private String cep;

	
	/**
	 * Metodo que formata o cep 00000-000
	 * Extrai os 5 primeiros digitos coloca um " - " depois deles e concatena com os numeros restantes depois dos 5 primeiros
	 * @return
	 */
	public String getFormattedCep() {
		return cep.substring(0, 5) + "-" + cep.substring(5);
	}
}
