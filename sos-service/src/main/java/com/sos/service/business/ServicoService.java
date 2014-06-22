package com.sos.service.business;

import java.util.List;

import com.sos.entities.Servico;
import com.sos.entities.TipoServico;
import com.sos.service.business.util.FiltroServicos;
import com.sos.service.util.exception.ServiceException;

public interface ServicoService{
	Servico findByCodigo(final Long codigo) throws ServiceException;
	List<Servico> findAllSortByValor() throws ServiceException;
	List<Servico> findAllSortByDescricao() throws ServiceException;
	List<Servico> findByDescricao(final String descricao);
	List<Servico> findByTipoServico(final TipoServico tipoServico);
	List<Servico> findByValor(double valor);
	List<Servico> findByPrestadorEmail(String email);
	List<Servico> findByFiltroServico(FiltroServicos filtro) throws ServiceException;
	
	void create(final Servico servico) throws ServiceException;
	void update(final Servico servico) throws ServiceException;
	void delete(final Servico servico) throws ServiceException;
}