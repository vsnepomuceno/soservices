package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Prestador;

public interface PrestadorRepositorio extends JpaRepository<Prestador, Long>{
	Prestador findByCPF(final String cpf);
}
