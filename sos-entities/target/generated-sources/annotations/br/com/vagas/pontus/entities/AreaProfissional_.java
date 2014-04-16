package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AreaProfissional.class)
public abstract class AreaProfissional_ {

	public static volatile SetAttribute<AreaProfissional, Vaga> vagas;
	public static volatile SingularAttribute<AreaProfissional, Integer> id;
	public static volatile SingularAttribute<AreaProfissional, AreaProfissional> areaProfissionalPai;
	public static volatile SetAttribute<AreaProfissional, AreaProfissional> areasProfissionais;
	public static volatile SetAttribute<AreaProfissional, Candidato> candidatos;
	public static volatile SingularAttribute<AreaProfissional, String> descricaoAreaProfissional;

}

