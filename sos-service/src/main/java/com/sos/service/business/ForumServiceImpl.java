package com.sos.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Forum;
import com.sos.service.business.util.validators.ForumValidator;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.ForumRepository;
import com.sos.service.util.exception.ServiceException;

public class ForumServiceImpl implements ForumService{

	@Autowired
	ForumRepository forumRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Forum findByCodigo(Long codigo) throws ServiceException {
		return forumRepository.findOne(codigo);
	}	
	

	@Override
	@Transactional
	public void create(Forum forum) throws ServiceException {
		ResultadoValidacao resultadoValidacao = ForumValidator.validarCamposForum(forum);
		if(resultadoValidacao.isValido()){
			forumRepository.save(forum);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Forum forum) throws ServiceException {
		create(forum);
	}

	@Override
	@Transactional
	public void delete(Forum forum) throws ServiceException {
		forumRepository.delete(forum);
	}
}