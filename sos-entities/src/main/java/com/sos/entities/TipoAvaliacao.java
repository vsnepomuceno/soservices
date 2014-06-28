package com.sos.entities;

public enum TipoAvaliacao {

	PRESTADOR("Prestador"),
	USUARIO("Usuário");
	
	private String tipoAvaliacao;
	
	private TipoAvaliacao(String tipoAvaliacao){
		this.tipoAvaliacao = tipoAvaliacao;
	}

	public String getTipoAvaliacao() {
		return tipoAvaliacao;
	}
}