package com.sos.api.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ServicoExclusionStrategy implements ExclusionStrategy {
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getName().equals("servicos") || f.getName().equals("forum")
				|| f.getName().equals("cpf") || f.getName().equals("credenciais") 
				|| f.getName().equals("senha");
	}
}