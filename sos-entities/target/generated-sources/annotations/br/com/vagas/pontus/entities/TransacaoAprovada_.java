package br.com.vagas.pontus.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransacaoAprovada.class)
public abstract class TransacaoAprovada_ {

	public static volatile SingularAttribute<TransacaoAprovada, Long> id;
	public static volatile SingularAttribute<TransacaoAprovada, Vaga> vaga;
	public static volatile SingularAttribute<TransacaoAprovada, Candidato> candidato;
	public static volatile SingularAttribute<TransacaoAprovada, Boolean> enviadoSucesso;
	public static volatile SingularAttribute<TransacaoAprovada, Date> dataTransacao;

}

