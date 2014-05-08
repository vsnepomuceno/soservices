package com.sos.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Servico;
import com.sos.entities.TipoServico;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.business.util.validators.ServicoValidator;
import com.sos.service.repository.ServicoRepository;
import com.sos.service.util.exception.ServiceException;

@Service
public class ServicoServiceImpl implements ServicoService {

	@Autowired
	ServicoRepository servicoRepository;

	@Override
	@Transactional(readOnly=true)
	public Servico findByCodigo(Long codigo) throws ServiceException {
		return servicoRepository.findOne(codigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findAllSortByValor() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "valor");
		return servicoRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findAllSortByDescricao() throws ServiceException {
		Sort sort = new Sort(Sort.Direction.ASC, "descricao");
		return servicoRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByDescricao(String descricao) {
		return servicoRepository.findByDescricao(descricao);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByTipoServico(TipoServico tipoServico) {
		return servicoRepository.findByTipoServico(tipoServico);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Servico> findByValor(double valor) {
		return servicoRepository.findByValor(valor);
	}

	@Override
	@Transactional
	public void create(Servico servico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = ServicoValidator.validarCamposServico(servico);
		
		if(resultadoValidacao.isValido()){
			servicoRepository.save(servico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Servico servico) throws ServiceException {
		ResultadoValidacao resultadoValidacao = ServicoValidator.validarCamposServico(servico);
		
		if(resultadoValidacao.isValido()){
			servicoRepository.save(servico);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void delete(Servico servico) throws ServiceException {
		servicoRepository.delete(servico);
	}
}