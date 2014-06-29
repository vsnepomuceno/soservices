package com.sos.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>{
	//List<Avaliacao> findByTipoAvaliacaoUsuario(TipoAvaliacao tipoAvaliacao, Usuario usuariou);
	List<Avaliacao> findByUsuarioId(Long codigo);
}