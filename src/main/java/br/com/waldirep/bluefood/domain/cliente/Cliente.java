package br.com.waldirep.bluefood.domain.cliente;


import javax.persistence.Entity;

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

	private String cpf;
	
	private String cep;

}
