package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
}