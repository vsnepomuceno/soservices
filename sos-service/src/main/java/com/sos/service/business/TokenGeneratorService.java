package com.sos.service.business;

import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.util.exception.ServiceException;

public interface TokenGeneratorService {
	Token create(Usuario usuario) throws ServiceException;
	void delete(final Token token) throws ServiceException;
	void deleteAllByUsuario(Usuario usuario) throws ServiceException;
	Token findByApiKeyAndUsuarioId(String apiKey, Long id) throws ServiceException;
}