package com.sos.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Avaliacao;
import com.sos.entities.TipoAvaliacao;
import com.sos.entities.Usuario;
import com.sos.service.business.util.validators.AvaliacaoValidator;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.AvaliacaoRepository;
import com.sos.service.util.exception.ServiceException;

public class AvaliacaoServiceImpl implements AvaliacaoService{

	@Autowired
	AvaliacaoRepository avaliacaoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Avaliacao findByCodigo(Long codigo) throws ServiceException {
		return avaliacaoRepository.findOne(codigo);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Avaliacao> findByTipoAvaliacaoUsuario(TipoAvaliacao tipoAvaliacao, Usuario usuariou) {
		return null;//avaliacaoRepository.findByTipoAvaliacaoUsuario(tipoAvaliacao, usuariou);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Avaliacao> findByUsuarioId(Long codigo) throws ServiceException {
		return avaliacaoRepository.findByUsuarioId(codigo);
	}

	@Override
	@Transactional
	public void create(Avaliacao avaliacao) throws ServiceException {
		ResultadoValidacao resultadoValidacao = AvaliacaoValidator.validarCamposAvaliacao(avaliacao);
		if(resultadoValidacao.isValido()){
			avaliacaoRepository.save(avaliacao);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Avaliacao avaliacao) throws ServiceException {
		create(avaliacao);
	}

	@Override
	@Transactional
	public void delete(Avaliacao avaliacao) throws ServiceException {
		avaliacaoRepository.delete(avaliacao);
	}
}