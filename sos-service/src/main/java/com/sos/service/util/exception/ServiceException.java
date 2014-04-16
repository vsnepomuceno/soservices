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
}