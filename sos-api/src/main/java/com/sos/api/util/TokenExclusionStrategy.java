package com.sos.api.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TokenExclusionStrategy implements ExclusionStrategy {
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getName().equals("cpf") || f.getName().equals("id") || f.getName().equals("credenciais")
				|| f.getName().equals("endereco") || f.getName().equals("nota") || f.getName().equals("senha")
				|| f.getName().equals("servicos") || f.getName().equals("telefone");
	}
}