package br.com.vagas.pontus.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransacaoSMS.class)
public abstract class TransacaoSMS_ {

	public static volatile SingularAttribute<TransacaoSMS, Long> id;
	public static volatile SingularAttribute<TransacaoSMS, Vaga> vaga;
	public static volatile SingularAttribute<TransacaoSMS, Candidato> candidato;
	public static volatile SingularAttribute<TransacaoSMS, Date> dataTransacao;
	public static volatile SingularAttribute<TransacaoSMS, EStatusSMS> statusSMS;

}

