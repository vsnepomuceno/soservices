package com.sos.service.business;

import java.util.List;

import com.sos.entities.Usuario;
import com.sos.service.util.exception.ServiceException;

public interface UsuarioService {
	Usuario findByCodigo(final Long codigo);
	List<Usuario> findAll();
	Usuario findByLogin(final String nomeUsuario);
	void cadastrarUsuario(Usuario usuario) throws ServiceException;
	void editarUsuario(Usuario usuario);
}