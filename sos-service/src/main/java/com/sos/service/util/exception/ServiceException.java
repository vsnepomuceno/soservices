package com.sos.service.util.exception;

import java.util.List;

public class ServiceException extends Exception{

	private static final long serialVersionUID = 4898310844546392700L;
	private List<String> msgs;
	
	public ServiceException(String msg, Throwable e){
		super(msg, e);
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(List<String> msgs) {
		this.msgs = msgs;
	}

	public List<String> getMsgs() {
		return msgs;
	}
	
	@Override
	public String getMessage() {
		String retorno = super.getMessage();
		if(retorno == null){
			StringBuilder sb = new StringBuilder();
			for (String mensagem : msgs) {
				sb.append(mensagem);
				sb.append(". ");
			}
			retorno = sb.toString();
		}
		return retorno;
	}
}	