package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Papel;
import com.sos.service.util.exception.ServiceException;

public interface PapelRepository extends JpaRepository<Papel, Long>{
	Papel findByPapel(final String papel) throws ServiceException;
}