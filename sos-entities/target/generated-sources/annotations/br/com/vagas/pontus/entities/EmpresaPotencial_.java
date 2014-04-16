package br.com.vagas.pontus.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmpresaPotencial.class)
public abstract class EmpresaPotencial_ {

	public static volatile SingularAttribute<EmpresaPotencial, Long> id;
	public static volatile SingularAttribute<EmpresaPotencial, Cidade> cidade;
	public static volatile SingularAttribute<EmpresaPotencial, String> nomeResponsavel;
	public static volatile SingularAttribute<EmpresaPotencial, ENacionalidade> nacionalidade;
	public static volatile SingularAttribute<EmpresaPotencial, String> email;
	public static volatile SingularAttribute<EmpresaPotencial, String> nomeFantasia;
	public static volatile SingularAttribute<EmpresaPotencial, String> telefone;
	public static volatile SingularAttribute<EmpresaPotencial, ESegmento> segmento;
	public static volatile SingularAttribute<EmpresaPotencial, String> cargoResponsavel;

}

