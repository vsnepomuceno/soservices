package com.sos.service.repository;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{
	Prestador findByCpf(final String cpf);
	Prestador findByEmail(final String email);
	List<Prestador> findByServicosTipoServico(final TipoServico tipoServico);
	List<Prestador> findAll(Specification<T> spec);
}