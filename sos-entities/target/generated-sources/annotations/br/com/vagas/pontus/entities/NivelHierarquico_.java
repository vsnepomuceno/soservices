package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NivelHierarquico.class)
public abstract class NivelHierarquico_ {

	public static volatile SetAttribute<NivelHierarquico, Vaga> vagas;
	public static volatile SingularAttribute<NivelHierarquico, Integer> id;
	public static volatile SetAttribute<NivelHierarquico, Candidato> candidatos;
	public static volatile SingularAttribute<NivelHierarquico, String> descricaoNivelHierarquico;

}

