package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cidade.class)
public abstract class Cidade_ {

	public static volatile SetAttribute<Cidade, Vaga> vagas;
	public static volatile SingularAttribute<Cidade, Integer> id;
	public static volatile SetAttribute<Cidade, Empresa> empresas;
	public static volatile SingularAttribute<Cidade, EEstado> estado;
	public static volatile SetAttribute<Cidade, Candidato> candidatos;
	public static volatile SingularAttribute<Cidade, String> descricaoCidade;

}

