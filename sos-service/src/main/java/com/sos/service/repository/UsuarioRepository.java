package com.sos.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Papel;
import com.sos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByLogin(final String nomeUsuario);
	List<Usuario> findByPapeis(Papel papel);
}