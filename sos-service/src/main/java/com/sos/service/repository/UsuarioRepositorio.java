package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
	Usuario findByNome(final String nome);
	Usuario findByEmail(final String email);
}
