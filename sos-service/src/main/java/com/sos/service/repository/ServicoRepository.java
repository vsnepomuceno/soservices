package com.sos.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Servico;
import com.sos.entities.TipoServico;

public interface ServicoRepository extends JpaRepository<Servico, Long>{
	List<Servico> findByDescricao(final String descricao);
	List<Servico> findByTipoServico(final TipoServico tipoServico);
	List<Servico> findByValor(double valor);
	List<Servico> findByPrestadorEmail(String email);
}