package com.sos.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sos.entities.Post;
import com.sos.service.business.util.validators.PostValidator;
import com.sos.service.business.util.validators.ResultadoValidacao;
import com.sos.service.repository.PostRepository;
import com.sos.service.util.exception.ServiceException;

public class PostServiceImpl implements PostService{

	@Autowired
	PostRepository postRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Post findByCodigo(Long codigo) throws ServiceException {
		return postRepository.findOne(codigo);
	}	
	

	@Override
	@Transactional
	public void create(Post post) throws ServiceException {
		ResultadoValidacao resultadoValidacao = PostValidator.validarCamposPost(post);
		if(resultadoValidacao.isValido()){
			postRepository.save(post);
		}else{
			throw new ServiceException(resultadoValidacao.getMsgs());
		}
	}

	@Override
	@Transactional
	public void update(Post post) throws ServiceException {
		create(post);
	}

	@Override
	@Transactional
	public void delete(Post post) throws ServiceException {
		postRepository.delete(post);
	}
}