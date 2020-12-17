package br.com.waldirep.bluefood.domain.pagamento;

import javax.validation.constraints.Pattern;

public class DadosCartao {
	
	
	@Pattern(regexp = "\\d{16}", message = "O número do cartão e inválido") // Validação do campo que so aceita 16 digitos(numeros) seguidos
	private String numCartao;
	
	
	
	
	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}
	
	public String getNumCartao() {
		return numCartao;
	}

}
