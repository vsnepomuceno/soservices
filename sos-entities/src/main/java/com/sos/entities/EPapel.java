package com.sos.entities;

public enum EPapel {

	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_EMPRESA("ROLE_EMPRESA"),
	ROLE_USER("ROLE_USER"),
	ROLE_OPERADOR("ROLE_OPERADOR");
	
	private String papel;
	
	private EPapel(String papel){
		this.papel = papel;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}
	
	@Override
	public String toString(){
		return papel;
	}
	
	public Integer getId(){
		return this.ordinal() + 1;
	}
}