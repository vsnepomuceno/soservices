package com.sos.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{
	Prestador findByCpf(final String cpf);
	List<Prestador> findByServicosTipoServico(final TipoServico tipoServico);
}