package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Token;
import com.sos.entities.Usuario;
import com.sos.service.util.exception.ServiceException;

public interface TokenGeneratorRepository extends JpaRepository<Token, Long>{
	Token findByApiKeyAndUsuarioId(final String apiKey, final Long id) throws ServiceException;
	Token findByUsuario(final Usuario usuario) throws ServiceException;
}