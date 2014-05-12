package com.sos.service.business;

import java.util.List;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;
import com.sos.service.util.exception.ServiceException;

public interface PrestadorService {
	Prestador findByCodigo(final Long codigo) throws ServiceException;
	List<Prestador> findAllSortByName() throws ServiceException;
	void create(final Prestador prestador) throws ServiceException;
	void update(final Prestador prestador) throws ServiceException;
	void delete(final Prestador prestador) throws ServiceException;
	Prestador findByCPF(String cpf) throws ServiceException;
	List<Prestador> findByServicosTipoServico(TipoServico tipoServico) throws ServiceException;
}
