package com.sos.service.business.util;

import java.util.List;

public class ResultadoValidacao {

	private boolean valido;
	private List<String> msgs;
	
	public ResultadoValidacao(boolean valido, List<String> msgs){
		this.valido = valido;
		this.msgs = msgs;
	}

	public boolean isValido() {
		return valido;
	}

	public List<String> getMsgs() {
		return msgs;
	}
}