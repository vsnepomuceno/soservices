package com.sos.service.business;

import com.sos.entities.Post;
import com.sos.service.util.exception.ServiceException;

public interface PostService{
	Post findByCodigo(Long codigo) throws ServiceException;
	
	void create(Post post) throws ServiceException;
	void update(Post post) throws ServiceException;
	void delete(Post post) throws ServiceException;
}