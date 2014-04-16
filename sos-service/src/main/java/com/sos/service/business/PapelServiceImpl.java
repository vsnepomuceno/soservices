package com.sos.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.entities.Papel;
import com.sos.service.repository.PapelRepository;
import com.sos.service.util.exception.ServiceException;

@Service
public class PapelServiceImpl implements PapelService {

	@Autowired
	PapelRepository papelRepository;
	
	@Override
	public Papel findByPapel(String papel) throws ServiceException {
		return papelRepository.findByPapel(papel);
	}
}