package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.TipoServico;

public interface TipoServicoRepository extends JpaRepository<TipoServico, Long>{
	TipoServico findByNome(final String nome);
}