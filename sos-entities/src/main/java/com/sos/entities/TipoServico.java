package com.sos.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipo_servico")
public class TipoServico implements Serializable{

	private static final long serialVersionUID = 6897718017222545185L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", updatable=false)
	private Long id;
	
	@Column(name="nome", nullable=false, length=200, unique=true)
	private String nome;
	
	@Column(name="valorado", nullable=false)
	private Boolean valorado;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="tipoServico", fetch=FetchType.LAZY)
	private Set<Servico> servicos; 
 
	@Override
	public String toString() {
		return "TipoServico [id=" + id + ", nome=" + nome + ", valorado=" + valorado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (valorado ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoServico other = (TipoServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valorado != other.valorado)
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean isValorado() {
		return valorado;
	}

	public void setValorado(Boolean valorado) {
		this.valorado = valorado;
	}

	public Long getId() {
		return id;
	}

	public Set<Servico> getServicos() {
		if(servicos == null){
			servicos = new HashSet<Servico>();
		}
		return servicos;
	}
}