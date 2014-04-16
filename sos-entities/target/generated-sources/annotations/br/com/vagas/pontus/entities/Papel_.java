package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Papel.class)
public abstract class Papel_ {

	public static volatile SingularAttribute<Papel, Long> id;
	public static volatile SingularAttribute<Papel, String> papel;
	public static volatile SetAttribute<Papel, Usuario> usuarios;

}

