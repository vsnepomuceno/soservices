package com.sos.service.business;

import java.util.List;

import com.sos.entities.Avaliacao;
import com.sos.entities.TipoAvaliacao;
import com.sos.entities.Usuario;
import com.sos.service.util.exception.ServiceException;

public interface AvaliacaoService{
	Avaliacao findByCodigo(Long codigo) throws ServiceException;
	List<Avaliacao> findByTipoAvaliacaoUsuario(TipoAvaliacao tipoAvaliacao, Usuario usuariou);
	List<Avaliacao> findByUsuarioId(Long codigo) throws ServiceException;
	
	void create(Avaliacao avaliacao) throws ServiceException;
	void update(Avaliacao avaliacao) throws ServiceException;
	void delete(Avaliacao avaliacao) throws ServiceException;
}