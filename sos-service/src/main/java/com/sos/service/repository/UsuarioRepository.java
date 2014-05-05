package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByEmail(final String email);
	Usuario findByNome(final String nome);
	Usuario findByEmailAndSenha(final String email, final String senha);
}
