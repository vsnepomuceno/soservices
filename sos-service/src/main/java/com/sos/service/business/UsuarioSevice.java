package com.sos.service.business;

import java.util.List;

import com.sos.entities.Usuario;
import com.sos.service.util.exception.ServiceException;

public interface UsuarioSevice {
	Usuario findByCodigo(final Long codigo) throws ServiceException;
	List<Usuario> findAllSortByName() throws ServiceException;
	void create(final Usuario usuario) throws ServiceException;
	void update(final Usuario usuario) throws ServiceException;
	void delete(final Usuario usuario) throws ServiceException;
	Usuario findByNome(String nome) throws ServiceException;
	Usuario findByEmail(String email) throws ServiceException;
}
