package com.sos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="avaliacao")
public class Avaliacao implements Serializable{

	private static final long serialVersionUID = -8947438896975933075L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", updatable=false)
	private Long id;
	
	@Column(name="depoimento", nullable=true, length=1000)
	private String depoimento;
	
	@Column(name="replica", nullable=true, length=1000)
	private String replica;
	
	@Column(name="nota", nullable=true)
	private Integer nota;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="tipo_avaliacao")
	private TipoAvaliacao tipoAvaliacao;

	@ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="usuario_avaliador_id")
	private Usuario usuarioAvaliador;
	
	public Avaliacao(){
		
	}

	@Override
	public String toString() {
		return "Avaliacao [id=" + id + ", depoimento=" + depoimento
				+ ", replica=" + replica + ", nota=" + nota
				+ ", tipoAvaliacao=" + tipoAvaliacao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depoimento == null) ? 0 : depoimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nota == null) ? 0 : nota.hashCode());
		result = prime * result + ((replica == null) ? 0 : replica.hashCode());
		result = prime * result + ((tipoAvaliacao == null) ? 0 : tipoAvaliacao.hashCode());
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
		Avaliacao other = (Avaliacao) obj;
		if (depoimento == null) {
			if (other.depoimento != null)
				return false;
		} else if (!depoimento.equals(other.depoimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nota == null) {
			if (other.nota != null)
				return false;
		} else if (!nota.equals(other.nota))
			return false;
		if (replica == null) {
			if (other.replica != null)
				return false;
		} else if (!replica.equals(other.replica))
			return false;
		if (tipoAvaliacao != other.tipoAvaliacao)
			return false;
		return true;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepoimento() {
		return depoimento;
	}

	public void setDepoimento(String depoimento) {
		this.depoimento = depoimento;
	}

	public String getReplica() {
		return replica;
	}

	public void setReplica(String replica) {
		this.replica = replica;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public TipoAvaliacao getTipoAvaliacao() {
		return tipoAvaliacao;
	}

	public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
		this.tipoAvaliacao = tipoAvaliacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUsuarioAvaliador() {
		return usuarioAvaliador;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuarioAvaliador(Usuario usuarioAvaliador) {
		this.usuarioAvaliador = usuarioAvaliador;
	}	
}