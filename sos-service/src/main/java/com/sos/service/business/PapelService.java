package com.sos.service.business;

import com.sos.entities.Papel;
import com.sos.service.util.exception.ServiceException;

public interface PapelService {
	Papel findByPapel(final String papel) throws ServiceException;
}
