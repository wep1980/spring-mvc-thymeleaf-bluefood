package br.com.waldirep.bluefood.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	// M�todo criado pelo padr�o de assinatura
	public Cliente findByEmail(String email);

}
