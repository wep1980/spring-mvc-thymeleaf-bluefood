package br.com.waldirep.bluefood.domain.pagamento;

public enum StatusPagemento {
	
	
	Autorizado("Autorizado"),
	NaoAutorizado("Não autorizado pela instituição financeira , o número do cartão deve iniciar com 1111"),
	CartaoInvalido("Cartão inválido , você deve digitar 16 números");
	
	String descricao;
	
	
	
	private StatusPagemento(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

}
