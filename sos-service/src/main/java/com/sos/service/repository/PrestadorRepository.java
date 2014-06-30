package com.sos.service.repository;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.entities.Prestador;
import com.sos.entities.TipoServico;

public interface PrestadorRepository extends JpaRepository<Prestador, Long>{
	Prestador findByCpf(final String cpf);
	Prestador findByEmail(final String email);
	List<Prestador> findByServicosTipoServico(final TipoServico tipoServico);
	List<Prestador> findAll(Specification<T> spec);
	
	@Query(nativeQuery=true, value="SELECT SUM(a.nota)/COUNT(1) AS media "
			+ "FROM usuario u INNER JOIN avaliacao a ON (u.id = a.usuario_id) WHERE a.usuario_id = ?1 ")
	BigDecimal findNotaPrestadorById(final Long id);
}