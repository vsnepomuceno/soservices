package com.sos.service.business;

import java.util.List;

import com.sos.entities.TipoServico;
import com.sos.service.util.exception.ServiceException;

public interface TipoServicoService{
	TipoServico findByCodigo(final Long codigo) throws ServiceException;
	List<TipoServico> findAllSortByName() throws ServiceException;
	void create(final TipoServico tipoServico) throws ServiceException;
	void update(final TipoServico tipoServico) throws ServiceException;
	void delete(final TipoServico tipoServico) throws ServiceException;
	TipoServico findByNome(String nome) throws ServiceException;
}