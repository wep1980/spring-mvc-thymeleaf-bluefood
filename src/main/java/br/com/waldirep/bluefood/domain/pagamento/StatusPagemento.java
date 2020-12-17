package br.com.waldirep.bluefood.domain.pagamento;

public enum StatusPagemento {
	
	
	Autorizado("Autorizado"),
	NaoAutorizado("Não autorizado pela instituição financeira"),
	CartaoInvalido("Cartão inválido ou bloqueado");
	
	String descricao;
	
	
	
	private StatusPagemento(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

}
