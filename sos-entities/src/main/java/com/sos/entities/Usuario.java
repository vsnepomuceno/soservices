package com.sos.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario", updatable=false)
	private Long id;
	
	@Column(name="nome", nullable=false, length=70, updatable=false)
	private String nome;
	
	@Column(name="cpf", nullable=false, length=14, updatable=false)
	private String cpf;
	
	@Column(name="username", updatable=false, length=45, nullable=false, unique=true)
	private String login;
	
	@Column(name="senha", length=45, nullable=false)
	private String senha;
	
	@Column(name="ativo")
	private boolean ativo;

	@Column(name="dataCadastro", updatable=false)
	private Date dataCadastro;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_papel", joinColumns={@JoinColumn(name="id_usuario", nullable=false, updatable=false)}, 
			inverseJoinColumns = {@JoinColumn(name = "idPapel", nullable = false, updatable = false) })
	private Set<Papel> papeis;
	
	public Usuario(){
		
	}
}