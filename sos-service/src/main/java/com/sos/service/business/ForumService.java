package com.sos.service.business;

import com.sos.entities.Forum;
import com.sos.service.util.exception.ServiceException;

public interface ForumService{
	Forum findByCodigo(Long codigo) throws ServiceException;
	
	void create(Forum forum) throws ServiceException;
	void update(Forum forum) throws ServiceException;
	void delete(Forum forum) throws ServiceException;
}