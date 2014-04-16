package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Beneficio.class)
public abstract class Beneficio_ {

	public static volatile SetAttribute<Beneficio, Vaga> vagas;
	public static volatile SingularAttribute<Beneficio, Integer> id;
	public static volatile SingularAttribute<Beneficio, String> descricaoBeneficio;

}

